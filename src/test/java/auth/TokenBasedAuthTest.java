package auth;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class TokenBasedAuthTest {
    @Test
    public void testTokenBasedAuth() {
        Response response = given()
                .queryParam("username", "Jack")
                .queryParam("password", "12345")
                .when()
                .post("http://localhost:8082/login");
        response.then().log().all();

        given()
                .cookies(response.getCookies())
                .when()
                .get("http://localhost:8082/homepage")
                .then()
                .log().all();
    }
}
