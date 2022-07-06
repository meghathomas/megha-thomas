package stepDefinitions.user;

import common.users.LoginAPI;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import base.Utils;
import org.junit.Assert;
import pojo.user.UserAPIResponse;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LoginLogoutStepDefn {
    private Response res = null;
    private RequestSpecification requestSpec = null;

    LoginAPI loginAPI=new LoginAPI();
    String username,password;

    @Before
    public void setup() throws IOException {
        RestAssured.baseURI = Utils.getDataFromPropertiesFile("baseURL");
    }

    @After
    public void tearDown(){

        RestAssured.reset();
    }

    @Given("I provide login credentials {string} and {string}")
    public void i_provide_login_credentials_and(String name, String pass) throws FileNotFoundException {
        username=name;
        password=pass;
        String urlPath = "/user/login?username=" + username + "&password=" + password;
        requestSpec = loginAPI.givenUserDetails(urlPath, username,password);
    }
    @When("I send request to login")
    public void i_send_request_to_login() {
        res = loginAPI.postLoginRequest(requestSpec);
    }
    @Then("login is successful")
    public void login_is_successful() {
        loginAPI.verifyLoginSuccess(res, username,password);
    }
    @Then("logout is successful")
    public void logout_is_successful() throws FileNotFoundException {
        String urlPath = "/user/logout";
        //Verify logout successful
        UserAPIResponse expectedResponse= loginAPI.verifyLogoutSuccess(urlPath);
        Assert.assertEquals("Status Check Passed!", "200", expectedResponse.getCode().toString());
        Assert.assertNotNull("type field in response is not empty", expectedResponse.getType());
        Assert.assertEquals("Message return id", "ok", expectedResponse.getMessage());
    }

    @Then("login failed")
    public void loginFailed() {
        loginAPI.verifyLoginFailure(res, username,password);
    }
}
