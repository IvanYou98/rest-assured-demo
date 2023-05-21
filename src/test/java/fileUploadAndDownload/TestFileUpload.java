package fileUploadAndDownload;
import org.apache.commons.io.FileUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.*;


public class TestFileUpload {
    @Test
    public void testFileUpload() {
        given()
                .contentType(ContentType.MULTIPART)
                .multiPart("file", new File("src/test/resources/sample-file1.txt"))
                .when()
                .post("https://the-internet.herokuapp.com/upload")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void testFileDownload() throws IOException {
        Response response = given()
                .when()
                .get("https://the-internet.herokuapp.com/download/ok.txt");
        File newFile = new File("download-file.txt");
        System.out.println(newFile);
        FileUtils.copyInputStreamToFile(response.asInputStream(), newFile);
    }
}
