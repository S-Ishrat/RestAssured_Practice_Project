import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class MyRestassured {
    Properties prop;
    public MyRestassured() throws IOException {
        prop=new Properties();
        FileInputStream fs=new FileInputStream("src/test/resources/config.properties");
        prop.load(fs);
    }
    @Test
    public void userlogin() throws ConfigurationException {
        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json").body("{\n" +
                "  \"email\": \"admin@dmoney.com\",\n" +
                "  \"password\": \"1234\"\n" +
                "}").when().post("/user/login");
       // System.out.println(res.asString());

        JsonPath jsonobj=res.jsonPath();
        String token=jsonobj.get("token");
        System.out.println(token);  //extracting token

        Utils.setEnv("token",token);

    }
    @Test
    public void serachUser() throws IOException {
        Properties prop=new Properties();
        FileInputStream fs=new FileInputStream("src/test/resources/config.properties");
        prop.load(fs);
        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json").header("Authorization","bearer "+prop.getProperty("token")).when().get("/user/search/id/28610");
        System.out.println(res.asString());
    }

    @Test
    public void createUser() throws IOException {

        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json").header("Authorization","bearer "+prop.getProperty("token")).header("X-AUTH-SECRET-KEY",prop.getProperty("secretkey")).body("{\n" +
                "  \"name\": \"newcustomer2224\",\n" +
                "  \"email\": \"newcustomer2224@gmail.com\",\n" +
                "  \"password\": \"1234\",\n" +
                "  \"phone_number\": \"00012345678\",\n" +
                "  \"nid\": \"12345777779\",\n" +
                "  \n" +
                "  \"role\":\"Customer\"\n" +
                " \n" +
                "}").when().post("user/create");
        System.out.println(res.asString());

        JsonPath jsonobj=res.jsonPath();
        String message=jsonobj.get("message");
        String userId=jsonobj.get("user.id");
        System.out.println(userId);
    }
}
