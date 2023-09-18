package sample;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("api")
public class HelloApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(HelloResource.class);
    }
}
