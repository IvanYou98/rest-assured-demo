package jsonParse;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import lombok.Data;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class TestPOJO {
    @Data
    public static class User {
        private Long id;
        private String email;
        private String first_name;
        private String last_name;
        private String avatar;
    }

    @Test
    public void testPOJOMapping() {
        JsonPath jsonPath = given()
                .contentType(ContentType.JSON)
                .pathParam("path", "users")
                .queryParam("page", 2)
                .when()
                .get("https://reqres.in/api/{path}")
                .then()
                .extract().response().jsonPath();
        List<String> emails = jsonPath.getList("data", User.class)
                .stream()
                .map(user -> user.getEmail())
                .collect(Collectors.toList());
        System.out.println(emails);
    }
}
