package cookieAndHeader;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class TestCookieAndHeader {
    @Test
    public void testGetCookies() {
        Response response = given()
                .when()
                .get("https://www.google.com");

        response.getCookies()
                .forEach((key, value) -> {
                    System.out.println("key: " + key + " value: " + value);
                });
    }

    @Test
    public void testValidateCookies() {
        given()
                .when()
                .get("https://www.google.com")
                .then()
                .cookie("1P_JAR", equalTo("2023-05-13-20"));
    }

    @Test
    public void testGetResponseHeaders() {
        Response response = given()
                .when()
                .get("https://www.google.com");

        response.getHeaders().forEach(header -> {
            System.out.println("name: " + header.getName() + " value: " + header.getValue());
        });
    }

    @Test
    public void testValidateResponseHeader() {
        given()
                .when()
                .get("https://www.google.com")
                .then()
                .header("Server", equalTo("gws"))
                .header("Content-Type", equalTo("text/html; charset=ISO-8859-1"));
    }
}
