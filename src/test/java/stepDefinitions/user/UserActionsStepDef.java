package stepDefinitions.user;

import common.users.LoginAPI;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.user.UserInfo;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class UserActionsStepDef {
    public Response res = null;
    public JsonPath jp = null;
    public RequestSpecification requestSpec = null;
    public UserInfo newUser = null;
    LoginAPI loginAPI=new LoginAPI();

    @Before
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @After
    public void tearDown() {
        RestAssured.reset();
    }

    @Given("^I provide below login information$")
    public void i_provide_login_information(List<Map<String, String>> listOfUserInfo) {
        Map<String, String> mapUserInfo = listOfUserInfo.get(0);
        newUser = loginAPI.createUserClass(mapUserInfo);

    }

    @When("^I send request to crete user$")
    public void i_send_request_to_crete_user() throws FileNotFoundException {
        res = loginAPI.createAndPostUserRequest(this.newUser);
    }

    @Then("^Create user is successful$")
    public void create_user_is_successful() {
        loginAPI.validateCreateUserResponse(res);
    }

    @And("^Allow to fetch the user details by \"([^\"]*)\"$")
    public void allow_to_fetch_the_user_details_by(String userName) throws FileNotFoundException {
        UserInfo actualUser = loginAPI.fetchUserInfoByUserName(userName);
        loginAPI.compareUserInfo("username", newUser,actualUser);

    }

    @And("^Allow to updated the First name \"([^\"]*)\" where Username \"([^\"]*)\"$")
    public void allow_to_updated_the_First_name_where_Username(String firstName,String userName) throws FileNotFoundException {
        UserInfo expectedUser = loginAPI.fetchUserInfoByUserName(userName);
        expectedUser.setFirstName(firstName);
        res = loginAPI.UpdateUserRequest(expectedUser);
        loginAPI.validateUpdateUserResponse(res);
        UserInfo actualUser = loginAPI.fetchUserInfoByUserName(userName);
        loginAPI.compareUserInfo("firstname", expectedUser,actualUser);
    }

    @And("^Allow to updated the Last name \"([^\"]*)\" where Username \"([^\"]*)\"$")
    public void allow_to_updated_the_Last_name_where_Username(String lastName,String userName) throws FileNotFoundException {
        UserInfo expectedUser = loginAPI.fetchUserInfoByUserName(userName);
        expectedUser.setLastName(lastName);
        res = loginAPI.UpdateUserRequest(expectedUser);
        loginAPI.validateUpdateUserResponse(res);
        UserInfo actualUser = loginAPI.fetchUserInfoByUserName(userName);
        loginAPI.compareUserInfo("lastname",expectedUser,actualUser);
    }

    @And("^Allow to updated the Email \"([^\"]*)\" where Username \"([^\"]*)\"$")
    public void allow_to_updated_the_Email_where_Username(String email,String userName) throws FileNotFoundException {
        UserInfo expectedUser = loginAPI.fetchUserInfoByUserName(userName);
        expectedUser.setEmail(email);
        res = loginAPI.UpdateUserRequest(expectedUser);
        loginAPI.validateUpdateUserResponse(res);
        UserInfo actualUser = loginAPI.fetchUserInfoByUserName(userName);
        loginAPI.compareUserInfo("email",expectedUser,actualUser);
    }

    @And("^Allow to updated password \"([^\"]*)\" where Username \"([^\"]*)\"$")
    public void allow_to_updated_password_where_Username(String password,String userName) throws FileNotFoundException {
        UserInfo expectedUser = loginAPI.fetchUserInfoByUserName(userName);
        expectedUser.setPassword(password);
        res = loginAPI.UpdateUserRequest(expectedUser);
        loginAPI.validateUpdateUserResponse(res);
        UserInfo actualUser = loginAPI.fetchUserInfoByUserName(userName);
        loginAPI.compareUserInfo("password",expectedUser,actualUser);
    }

    @And("^Allow to updated phone number \"([^\"]*)\" where Username \"([^\"]*)\"$")
    public void allow_to_updated_phone_number_where_Username(String phoneNumber,String userName) throws FileNotFoundException {
        UserInfo expectedUser = loginAPI.fetchUserInfoByUserName(userName);
        expectedUser.setPhone(phoneNumber);
        res = loginAPI.UpdateUserRequest(expectedUser);
        loginAPI.validateUpdateUserResponse(res);
        UserInfo actualUser = loginAPI.fetchUserInfoByUserName(userName);
        loginAPI.compareUserInfo("phone",expectedUser,actualUser);
    }

    @And("^Delete the user with username \"([^\"]*)\"$")
    public void delete_the_user_with_username(String userName) throws FileNotFoundException {
        loginAPI.deleteUserByUserName(userName);
    }

}
