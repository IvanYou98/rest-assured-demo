package quickStart;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestQuickStart {
    private int userId;
    private final String userName = "morpheus";

    @Test(priority = 0)
    public void testGetAllUsers() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=1&per_page=10")
                .then()
                .log().body();
    }

    @Test(priority = 1)
    public void createUser() {
        HashMap<String, String> payload = new HashMap<>();
        /*
           {
            "name": "morpheus",
            "job": "leader"
            }
         */
        payload.put("name", userName);
        payload.put("job", "leader");
        this.userId = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", equalTo(userName))
                .extract().body().jsonPath().getInt("id");
    }


    @Test(dependsOnMethods = "createUser")
    public void updateUser() {
        /*
           {
            "name": "morpheus",
            "job": "manager"
            }
         */
        HashMap<String, Object> updatedUser = new HashMap<>();
        updatedUser.put("name", userName);
        updatedUser.put("job", "manager");
        given().contentType(ContentType.JSON)
                .when()
                .body(updatedUser)
                .put("https://reqres.in/api/users/" + userId)
                .then()
                .log().body()
                .body("job", equalTo("manager"))
                .statusCode(200);
    }

    //    @Test(dependsOnMethods = "updateUser")
    @Test(dependsOnMethods = "updateUser")
    public void deleteUser() {
        given()
                .when()
                .delete("https://reqres.in/api/users/" + userId)
                .then()
                .log().body()
                .statusCode(204);
    }

}
