package auth;

import io.restassured.response.Response;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;

public class SessionBasedAuthTest {
    @Test
    public void testSessionBasedAuth() {
        Response response = given()
                .queryParam("username", "admin")
                .queryParam("password", "admin")
                .when()
                .post("http://localhost:8081/login");
        response.then().log().all();

        given().cookies(response.then().extract().cookies())
                .when()
                .get("http://localhost:8081")
                .then()
                .log().all();
    }
}
