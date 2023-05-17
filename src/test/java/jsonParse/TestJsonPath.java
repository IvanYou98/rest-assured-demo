package jsonParse;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.*;

public class TestJsonPath {
    private JsonPath jsonPath;

    @BeforeClass
    public void getProducts() {
        this.jsonPath = given().contentType(ContentType.JSON)
                .when()
                .get("https://fakestoreapi.com/products")
                .jsonPath();
    }

    @Test
    public void testGetInfoOfFirstProduct() {
        String title = jsonPath.getString("[0].title");
        int count = jsonPath.getInt("[0].rating.count");
        System.out.println("title: " + title);
        System.out.println("count: " + count);
    }

    @Test
    public void testGetTheTitlesOfAllProducts() {
        List<String> titles = jsonPath.getList("title", String.class);
        titles.forEach(System.out::println);
    }

    @Test
    public void testGetAvgCountsOfProduct() {
        List<Double> rates = jsonPath.getList("rating.rate", Double.class);
        List<Double> counts = jsonPath.getList("rating.count", Double.class);
        double sum = 0;
        for (int i = 0; i < rates.size(); i++) {
            sum += rates.get(i) * counts.get(i);
        }
        System.out.println(sum / rates.size());
    }

    @Test
    public void testGetAllProductsLessThan30() {
        List<Map<String, Object>> products = jsonPath.getList("findAll{ it.price < 30 }");
        products.forEach(p -> {
            System.out.println("title: " + p.get("title") + " price: " + p.get("price"));
        });
    }

    @Test
    public void testGetAllProductsInRange() {

        List<Map<String, Object>> products = jsonPath
                .param("min", 20)
                .param("max", 200)
                .getList("findAll{ it.price >= min && it.price <= max }");
        products.forEach(p -> {
            System.out.println("title: " + p.get("title") + " price: " + p.get("price"));
        });
    }

    @Test
    public void testPriceStatistics() {
        double maxPrice = jsonPath.getDouble("max{ it.price }.price");
        System.out.println("max price: " + maxPrice);
        double minPrice = jsonPath.getDouble("min{ it.price }.price");
        System.out.println("min price: " + minPrice);
        double sumPrice = jsonPath.getDouble("sum{ it.price }");
        System.out.println("sum of prices: " + sumPrice);
//        double totalPrice = jsonPath.getList("price", Double.class).stream().mapToDouble(p -> p).sum();
//        System.out.println("total price: " + totalPrice);
    }


    @Test
    public void testGetNumberOfProducts() {
        int length = jsonPath.getInt("size()");
        System.out.println(length);

        boolean isEmpty = jsonPath.getBoolean("isEmpty()");
        System.out.println(isEmpty);
    }


}
