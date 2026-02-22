package controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Properties;
import config.UserModel;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;
    public UserController(Properties prop){
        this.prop=prop;

    }
    public Response doLogin(UserModel userModel){
        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json").body(userModel).when().post("/user/login");

        return res;
    }

    public Response createUser(UserModel userModel){
        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json").header("Authorization","bearer "+prop.getProperty("token")).header("X-AUTH-SECRET-KEY",prop.getProperty("secretkey")).
                body(userModel).when().post("user/create");
        return res;
    }

    public Response serachUser(String userid)  {

        RestAssured.baseURI="https://dmoney.roadtocareer.net";
        Response res=given().contentType("application/json").header("Authorization","bearer "+prop.getProperty("token")).when().get("/user/search/id/"+userid);
        return res;
    }
}
