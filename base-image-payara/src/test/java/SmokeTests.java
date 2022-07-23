import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DisplayName("Payara Smoke Tests")
class SmokeTests {

    @Container
    GenericContainer<?> app = new GenericContainer<>(System.getProperty("DOCKER_IMAGE"))
            .withExposedPorts(8080)
            .waitingFor(Wait.forLogMessage(".*Payara Micro URLs.*\\n", 1));

    @Test
    @DisplayName("/ (GET)")
    public void homeEndpoint() {
        given().port(app.getFirstMappedPort())
                .when().get("/")
                .then().statusCode(404).body(containsString("HTTP Status 404"));
    }
}
