package chaining;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Test(groups = "update", dependsOnGroups = "get")
public class TestUpdate {
    @Parameters("token")
    public void testUpdateUser(ITestContext context, String token) {
        Faker faker = new Faker();
        JSONObject updatedUser = new JSONObject()
                .put("email", faker.internet().emailAddress())
                .put("name", faker.name().fullName())
                .put("gender", faker.options().option("male", "female"))
                .put("status", faker.options().option("active", "inactive"));
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", context.getAttribute("userId"))
                .body(updatedUser.toString())
                .when()
                .put("https://gorest.co.in/public/v2/users/{userId}");

        response.then().log().body();
        Assert.assertEquals(updatedUser.get("email"), response.jsonPath().getString("email"));
        Assert.assertEquals(updatedUser.get("name"), response.jsonPath().getString("name"));
        Assert.assertEquals(updatedUser.get("gender"), response.jsonPath().getString("gender"));
        Assert.assertEquals(updatedUser.get("status"), response.jsonPath().getString("status"));
    }
}
