package requestBody;

import io.restassured.http.ContentType;
import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;

public class TestBuildingRequestBody {
    private static final String URL = "https://reqres.in/api/users";
    @Test
    public void testJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "jack");
        jsonObject.put("job", "doctor");
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(jsonObject.toString())
                .post(URL)
                .then()
                .statusCode(201)
                .log().body();
    }

    @Data @Builder
    static class Person {
        private String name;
        private String job;
    }

    @Test
    public void testPOJO() {
        Person p = Person.builder()
                .job("teacher")
                .name("Jerry")
                .build();
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(p)
                .post(URL)
                .then()
                .log().body();
    }

    @Test
    public void testExternalFile() throws IOException {
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(Files.newInputStream(Paths.get("src/test/java/requestBody/person.json")))
                .post(URL)
                .then()
                .log().body();
    }
}
