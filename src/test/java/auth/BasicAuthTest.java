package auth;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;


public class BasicAuthTest {
    @Test
    public void testBasicAuth() {
        RestAssured.baseURI = "https://the-internet.herokuapp.com";
        given()
                .auth().basic("admin", "admin")
                .when()
                .get("/basic_auth")
                .then()
                .log().body()
                .statusCode(200);
    }
}
