package xmlParse;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

import static io.restassured.RestAssured.*;

public class TestXmlParse {
    Response response;

    @BeforeClass
    public void getLocations() {
        this.response = given()
                .queryParam("name", "New York City")
                .queryParam("username", "ivan")
                .queryParam("maxRows", 5)
                .when()
                .get("http://api.geonames.org/search")
                .then()
                .log().all()
                .extract().response();
    }
    @Test
    public void testGetGeoNameId() {
        String id = response.xmlPath().getString("geonames.geoname.find { it.name == 'East New York' }.geonameId");
        System.out.println(id);
    }

    @Test
    public void testResponseLength() {
        int rows = response.xmlPath()
                .getInt("geonames.geoname.size()");
        System.out.println(rows);
    }

    @Test
    public void testFindTheNameOfFirst3Cities() {
        List<String> names = response.xmlPath().getList("geonames.geoname.name[0..]", String.class);
        System.out.println(names);
    }

    @Test
    public void testFindCityNameStartsWithNew() {
        List<String> cityNames = response.xmlPath().getList("geonames.geoname.findAll { it.name =~ /^New.*/ }.name");
        cityNames.forEach(System.out::println);
    }
}
