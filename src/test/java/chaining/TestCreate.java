package chaining;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Test(groups = "create")
public class TestCreate {
    @Parameters({"token"})
    public void testCreate(ITestContext testContext, String token) {
        Faker faker = new Faker();
        JSONObject newUser = new JSONObject()
                .put("name", faker.name().fullName())
                .put("email", faker.internet().emailAddress())
                .put("gender", faker.options().option("male", "female"))
                .put("status", faker.options().option("active", "inactive"));

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(newUser.toString())
                .when()
                .post("https://gorest.co.in/public/v2/users");
        response.then().log().body();
        response.then().statusCode(201);
        testContext.setAttribute("userId", response.jsonPath().getLong("id"));
    }
}
