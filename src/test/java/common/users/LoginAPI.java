package common.users;

import base.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import pojo.user.UserAPIResponse;
import pojo.user.UserInfo;

import java.io.FileNotFoundException;
import java.util.Map;

public class LoginAPI {

    public RequestSpecification givenUserDetails(String urlPath, String username, String password) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder(urlPath,"application/json",null);
        System.out.println(RestAssured.given().spec(apiRequestBuilder.getRequestSpecification()));
        return RestAssured.given().spec(apiRequestBuilder.getRequestSpecification());

    }

    public Response postLoginRequest(RequestSpecification requestSpec) {
        Response res = requestSpec.when().get();
        return res;
    }

    public String verifyLoginSuccess(Response res, String username, String password) {
        JsonPath jp = res.jsonPath();
        Assert.assertEquals("Status Check Failed!", 200, res.getStatusCode());
        Assert.assertTrue(jp.get("message").toString().contains("logged in user session:"));
        return "Status check passed > Response "+ res.getStatusCode();
    }

    public UserAPIResponse verifyLogoutSuccess(String urlPath) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder(urlPath,"application/json",null);
        RequestSpecification requestSpec = RestAssured.given().spec(apiRequestBuilder.getRequestSpecification());
        Response res =  requestSpec.when().get();
        UserAPIResponse response =res.as(UserAPIResponse.class);
        return response;
    }

    public void verifyLoginFailure(Response res, String username, String password) {
        JsonPath jp = res.jsonPath();
        Assert.assertEquals("Invalid user credentials", 401, res.getStatusCode());
    }

    public UserInfo createUserClass(Map<String, String> userInfo) {
        UserInfo newUser = new UserInfo(Integer.parseInt(userInfo.get("id")), userInfo.get("userName"), userInfo.get("firstname"),
                userInfo.get("lastname"), userInfo.get("email"), userInfo.get("password"), userInfo.get("phoneNumber"),
                Integer.parseInt(userInfo.get("userStatus")));
        return newUser;

    }

    public Response createAndPostUserRequest(UserInfo newUser) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder("/user","application/json",newUser);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().post();
        return res;
    }

    public UserAPIResponse userResponseDeSerialization(Response res) {
        return res.as(UserAPIResponse.class);
    }

    public void validateCreateUserResponse(Response res) {
        UserAPIResponse userResponse = userResponseDeSerialization(res);
        Assert.assertEquals("Status Check Failed!", 200, res.getStatusCode());
        Assert.assertEquals("Validate Status code in response ", "200", userResponse.getCode()+"");
        Assert.assertEquals("Type ", "unknown", userResponse.getType());
        System.out.println("User message > "+ userResponse.getMessage());

    }

    public UserInfo fetchUserInfoByUserName(String userName) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder("/user/"+userName,"application/json",null);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().get();
        return res.as(UserInfo.class);
    }

    public Response UpdateUserRequest(UserInfo newUser) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder("/user/"+newUser.getUsername(),"application/json",newUser);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().put();
        return res;
    }

    public void validateUpdateUserResponse(Response res) {
        validateCreateUserResponse(res);

    }

    public void compareUserInfo(String field, UserInfo expected, UserInfo actual) {
        switch(field){
           case "username":
               Assert.assertEquals("Validate user name", expected.getUsername(), actual.getUsername());
               break;
           case "firstname":
               Assert.assertEquals("Validate user firstname", expected.getFirstName(), actual.getFirstName());
                break;
           case "lastname":
               Assert.assertEquals("Validate user lastname", expected.getLastName(), actual.getLastName());
                break;
            case "email":
                Assert.assertEquals("Validate user email", expected.getEmail(), actual.getEmail());
                break;
            case "password":
                Assert.assertEquals("Validate user password", expected.getPassword(), actual.getPassword());
                break;
            case "phone":
                Assert.assertEquals("Validate user phone", expected.getPhone(), actual.getPhone());
                break;
            case "userStatus":
                Assert.assertEquals("Validate user user status", expected.getUserStatus(), actual.getUserStatus());
                break;
        }
    }

    public void deleteUserByUserName(String userName) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder("/user/"+userName,"application/json",null);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().delete();
        UserAPIResponse userResponse = userResponseDeSerialization(res);
        Assert.assertEquals("Status Check Failed!", 200, res.getStatusCode());
        Assert.assertEquals("Validate Status code in response ", "200", userResponse.getCode()+"");
        Assert.assertEquals("Type ", "unknown", userResponse.getType());
        Assert.assertEquals("User message ", userName, userResponse.getMessage());
    }

    public void compareUserInfoAll(UserInfo expected, UserInfo actual) {
        Assert.assertEquals("Validate user name", expected.getUsername(), actual.getUsername());
        Assert.assertEquals("Validate user firstname", expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals("Validate user lastname", expected.getLastName(), actual.getLastName());
        Assert.assertEquals("Validate user email", expected.getEmail(), actual.getEmail());
        Assert.assertEquals("Validate user password", expected.getPassword(), actual.getPassword());
        Assert.assertEquals("Validate user phone", expected.getPhone(), actual.getPhone());
        Assert.assertEquals("Validate user user status", expected.getUserStatus(), actual.getUserStatus());
    }

}
