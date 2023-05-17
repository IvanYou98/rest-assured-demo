package auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuth2Test {
    @Test
    // https://github.com/login/oauth/authorize?
    // client_id= <your client id>
    // &redirect_uri=http://localhost:8083/repos
    // &scope=public_repo
    // &state=234234
    public void testOAuth2() {
        RestAssured.baseURI = "https://github.com";
        String responseString = given()
                .formParam("client_id", "you client id")
                .formParam("client_secret", "your client secret")
                .formParam("code", "683eaadf45ba96ea9e61")
                .formParam("redirect_uri", "http://localhost:8083/repos")
                .formParam("grant_type", "authorization_code")
                .when()
                .post("/login/oauth/access_token")
                .then()
                .log().body().extract().asString();
        String accessToken = responseString.split("&")[0].split("=")[1];
//        System.out.println("Access Token: " + accessToken);
        RestAssured.baseURI = "https://api.github.com";
        Response response = given()
                .auth().oauth2(accessToken)
                .when()
                .get("/user/repos");
//        response.then().log().body();
        List<String> repoNames = response.jsonPath().getList("name", String.class);
        repoNames.forEach(System.out::println);


    }
}
