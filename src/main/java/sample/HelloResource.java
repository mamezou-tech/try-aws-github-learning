package sample;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("hello")
public class HelloResource {

    @Inject
    @ConfigProperty(name = "config.val")
    private String configValue;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return configValue;
    }
}
