package sample;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.HelidonTest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@HelidonTest
@AddConfig(key = "server.port", value = "7001")
@ExtendWith(JulToSLF4DelegateExtension.class)
public class HelloResourceTest {

    private HelloResource helllResource;

    @BeforeEach
    public void setup() throws Exception {
        helllResource = RestClientBuilder.newBuilder().baseUri(new URI("http://localhost:7001/api"))
                .build(HelloResource.class);
    }

    @Test
    void tesHello() {
        var expected = "Hello";
        var actual = helllResource.hello();
        assertEquals(expected, actual);
    }

    @Path("hello")
    interface HelloResource {
        @GET
        @Produces(MediaType.TEXT_PLAIN)
        String hello();
    }
}
