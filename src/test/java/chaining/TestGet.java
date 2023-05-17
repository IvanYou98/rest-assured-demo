package chaining;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Test(groups = "get", dependsOnGroups = "create")
public class TestGet {
    @Parameters("token")
    public void testGetUser(ITestContext context, String token) {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", context.getAttribute("userId"))
                .when()
                .get("https://gorest.co.in/public/v2/users/{userId}")
                .then()
                .log().body()
                .statusCode(200);
    }
}
