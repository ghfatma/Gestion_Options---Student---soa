package utilities;

import ressourcesrest.restEtudiant;
import ressourcesrest.restOption;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest")
public class RestActivator extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(restOption.class);
        classes.add(restEtudiant.class);
        return classes;
    }
}