package pathAndQuery;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers.*;

public class TestPathAndQuery {

    // https://reqres.in/api/users?page=2
    @Test
    public void testPathAndQuery() {
        given()
                .pathParam("path", "users")
                .queryParam("page", 2)
                .when()
                .get("https://reqres.in/api/{path}")
                .then()
                .log().body()
                .statusCode(200);
    }
}
