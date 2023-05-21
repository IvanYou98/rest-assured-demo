package jsonParse;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Data;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestParse {
    private Response response;

    @BeforeClass
    public void sendRequest() {
        this.response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .when()
                .get("https://fakestoreapi.com/products/{id}");
    }

    /*
                id:1,
                title:'...',
                price:'...',
                category:'...',
                description:'...',
                image:'...'
     */
    @Data
    public static class Product {
        private Long id;
        private String title;
        private Double price;
        private String category;
        private String description;
        private String image;
        private Rating rating;
    }

    @Data
    public static class Rating {
        private double rate;
        private int count;
    }


    @Test
    public void testPOJOMapping() {
        // https://fakestoreapi.com/products/1
        Product product = response.then().extract().as(Product.class);
        System.out.println(product);
    }

    @Test
    public void testJsonObject() {
        JSONObject js = new JSONObject(response.body().asString());
        System.out.println(js);
    }


    @Test
    void testJsonPath() {
        double rate = response.jsonPath().getDouble("rating.rate");
        String title = response.jsonPath().getString("title");
        System.out.println("rate of " + title + " is: " + rate);
    }
}
