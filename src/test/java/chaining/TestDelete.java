package chaining;

import io.restassured.http.ContentType;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Test(groups = "delete", dependsOnGroups = "update")
public class TestDelete {
    @Parameters("token")
    public void testDeleteUser(ITestContext context, String token) {
        Object userId = context.getAttribute("userId");
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .pathParam("userId", userId)
                .when()
                .delete("https://gorest.co.in/public/v2/users/{userId}")
                .then()
                .statusCode(204);
    }
}
