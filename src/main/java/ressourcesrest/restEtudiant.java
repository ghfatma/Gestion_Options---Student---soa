package ressourcesrest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;
import utilities.Donnees;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("etudiants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class restEtudiant {

    private final EtudiantBusiness etudiantMetier = Donnees.ETUDIANTS;
    private final OptionBusiness optionMetier = Donnees.OPTIONS;

    @POST
    public Response ajouterEtudiant(Etudiant etudiant) {
        if (etudiant != null && etudiant.getOption() != null && etudiantMetier.addEtudiant(etudiant)) {
            return Response.status(Response.Status.OK).entity(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public Response listerEtudiants() {
        return Response.status(Response.Status.OK).entity(etudiantMetier.getAllEtudiants()).build();
    }

    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response listerEtudiantsParOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionMetier.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Etudiant> sansOption = new ArrayList<>();
        for (Etudiant e : etudiantMetier.getEtudiantsByOption(option)) {
            sansOption.add(new Etudiant(e.getIdentifiant(), e.getNom(), e.getPrenom(), null, e.getAnneeEtude(), e.getEmail()));
        }
        EtudiantList liste = new EtudiantList(sansOption);
        return Response.status(Response.Status.OK).entity(liste).build();
    }

    @GET
    @Path("{identifiant}")
    public Response getEtudiant(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = etudiantMetier.getEtudiantByIdentifiant(identifiant);
        if (etudiant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(etudiant).build();
    }

    @PUT
    @Path("{identifiant}")
    public Response modifierEtudiant(@PathParam("identifiant") String identifiant, Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Option option = optionMetier.getOptionByCode(etudiant.getOption().getCodeOption());
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        etudiant.setOption(option);
        if (etudiantMetier.updateEtudiant(identifiant, etudiant)) {
            return Response.status(Response.Status.OK).entity(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{identifiant}")
    public Response supprimerEtudiant(@PathParam("identifiant") String identifiant) {
        if (etudiantMetier.deleteEtudiant(identifiant)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}