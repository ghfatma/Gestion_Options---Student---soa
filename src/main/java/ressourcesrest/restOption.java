package ressourcesrest;

import entities.Option;
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
import java.util.List;

@Path("options")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class restOption {

    private final OptionBusiness optionMetier = Donnees.OPTIONS;

    @POST
    public Response ajouterOption(Option option) {
        if (option != null && optionMetier.addOption(option)) {
            return Response.status(Response.Status.OK).entity(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    public Response listerOptions(@QueryParam("domaine") String domaine) {
        List<Option> options;
        if (domaine == null || domaine.trim().isEmpty()) {
            options = optionMetier.getListeOptions();
        } else {
            options = optionMetier.getOptionsByDomaine(domaine);
        }
        return Response.status(Response.Status.OK).entity(options).build();
    }

    @GET
    @Path("{codeOption}")
    public Response getOption(@PathParam("codeOption") int codeOption) {
        Option option = optionMetier.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(option).build();
    }

    @PUT
    @Path("{codeOption}")
    public Response modifierOption(@PathParam("codeOption") int codeOption, Option option) {
        if (option != null && optionMetier.updateOption(codeOption, option)) {
            return Response.status(Response.Status.OK).entity(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{codeOption}")
    public Response supprimerOption(@PathParam("codeOption") int codeOption) {
        if (optionMetier.deleteOption(codeOption)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}