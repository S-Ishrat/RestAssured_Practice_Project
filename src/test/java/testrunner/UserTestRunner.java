package testrunner;

import com.github.javafaker.Faker;
import config.Setup;
import config.UserModel;
import controller.UserController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.Utils;

public class UserTestRunner extends Setup {
    UserController userController;
    @BeforeClass
    public void myUserControll(){
        userController=new UserController(prop);
    }
    @Test (priority = 1,description = "user can login")
    public void doLogin() throws ConfigurationException {
        //UserController userController=new UserController(prop);
        UserModel userModel=new UserModel();
        userModel.setEmail("admin@dmoney.com");
        userModel.setPassword("1234");
        Response res=userController.doLogin(userModel);

        JsonPath jsonobj=res.jsonPath();
        String token=jsonobj.get("token");
        Utils.setEnv("token",token);
        System.out.println(token);

    }
   // @Test (priority = 2,description = "user creation")
    public void createUser() throws ConfigurationException {
        UserController userController=new UserController(prop);
        UserModel userModel=new UserModel();
        Faker faker=new Faker();

        userModel.setName(faker.name().fullName());
        userModel.setEmail(faker.internet().emailAddress());
        userModel.setPassword("1234");
        userModel.setPhone_number("01361481846");
        userModel.setNid("109234256");
        userModel.setRole("Customer");
        Response res=userController.createUser(userModel);

        JsonPath jsonobj=res.jsonPath();
        String name=jsonobj.get("user.name");
        String email=jsonobj.get("user.email");
        String password=jsonobj.get("user.password");
        String phone_number=jsonobj.get("user.phone_number");
       int userId=jsonobj.get("user.id");

        Utils.setEnv("name",name);
       Utils.setEnv("id",String.valueOf(userId));
        Utils.setEnv("email",email);
        Utils.setEnv("phone_number",phone_number);

        System.out.println(res.asString());
    }
    @Test (priority = 3,description ="search by user")

    public void searchUser(){
        UserController userController=new UserController(prop);
        Response res=userController.serachUser(prop.getProperty("id"));
        System.out.println(res.asString());

        JsonPath jsonobj=res.jsonPath();
        //jsonobj.get("message");
        Assert.assertEquals(jsonobj.get("message"),"User found");

    }

}
