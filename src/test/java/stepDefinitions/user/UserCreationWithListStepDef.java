package stepDefinitions.user;

import base.RequestBuilder;
import base.Utils;
import common.users.LoginAPI;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.user.UserInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserCreationWithListStepDef {

    private Response res = null; // Response
    private RequestSpecification requestSpec = null;
    private List<UserInfo> userList = null;
    private UserInfo userArray[] = null;
    LoginAPI loginAPI=new LoginAPI();

    @Before
    public void setup() throws IOException {
        RestAssured.baseURI = Utils.getDataFromPropertiesFile("baseURL");;
    }

    @After
    public void tearDown() {
        RestAssured.reset();
    }

    @Given("^Create multiple user using below details using List$")
    public void create_multiple_user_using_below_details_using_list(List<Map<String, String>> listOfUserInfo) throws FileNotFoundException {
        userList = new ArrayList<UserInfo>();
        for (Map<String, String> map : listOfUserInfo) {
            userList.add(loginAPI.createUserClass(map));
        }

        RequestBuilder apiRequestBuilder = new RequestBuilder("/user/createWithArray","application/json",userList);
        requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
    }

    @When("^Send request to crete user with list$")
    public void send_request_to_crete_user_with_list(){
        res = requestSpec.when().post();
    }

    @Then("^Create user is successful with list$")
    public void create_user_is_successful_with_list() {
        loginAPI.validateCreateUserResponse(res);
    }

    @And("^Fetch the list of user details by \"([^\"]*)\"$")
    public void fetch_the_list_of_user_details_by(String userName) throws Exception {
        UserInfo actualUserData = loginAPI.fetchUserInfoByUserName(userName);
        UserInfo expectedUserData = null;
        for (UserInfo userInfo : this.userList) {
            if(userName.equals(userInfo.getUsername())) {
                expectedUserData = userInfo;
            }
        }
        loginAPI.compareUserInfoAll(expectedUserData,actualUserData);
    }
}
