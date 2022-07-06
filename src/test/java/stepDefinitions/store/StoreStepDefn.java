package stepDefinitions.store;

import base.Utils;
import common.store.StoreAPI;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import pojo.store.StoreInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StoreStepDefn {

    private Response res = null;
    private StoreInfo storeInfo = null;
    private static String storeUrl = null;
    StoreAPI storeAPISteps=new StoreAPI();

    @Before
    public void setup() throws IOException {
        RestAssured.baseURI = Utils.getDataFromPropertiesFile("baseURL");;
    }

    @After
    public void tearDown() {
        RestAssured.reset();
    }

    @Given("^I hit the \"([^\"]*)\"$")
    public void i_hit_the(String inventoryURL) throws Exception {
        this.res = storeAPISteps.fetchInventoryData(inventoryURL);
    }

    @When("^Request should submit and Positive API response should received \"([^\"]*)\"$")
    public void request_should_submit_and_Positive_API_response_should_received(String response) throws Exception {
        Assert.assertEquals("Status Check Passed!", response, res.getStatusCode()+"");
    }

    @Then("^Inventory data should display$")
    public void inventory_data_should_display() throws Exception {
        String response = res.body().asString();
        System.out.println("Inventory data " + response);
        Assert.assertNotNull("Inventory data should present ", response);
    }

    @Given("^Place an order for a  pet$")
    public void place_an_order_for_a_pet(List<Map<String, String>> listOfData) throws FileNotFoundException {
        Map<String,String> map = listOfData.get(0);
        StoreStepDefn.storeUrl = map.get("url");

        storeInfo = storeAPISteps.createStoreClass(map);
        res=storeAPISteps.createOrderRequest(StoreStepDefn.storeUrl, storeInfo);
        StoreInfo actualResponse = storeAPISteps.validatePostStatusAndReturnResponse(res);
        storeAPISteps.compareStoreInfo(storeInfo, actualResponse);
    }

    @When("^Fetch order information by \"([^\"]*)\"$")
    public void fetch_order_information_by(String orderId) throws FileNotFoundException {
        StoreInfo actualResponse = storeAPISteps.fetchOrderInfoById(StoreStepDefn.storeUrl,orderId);
        storeAPISteps.compareStoreInfo(storeInfo, actualResponse);

    }

    @Then("^Delete order by \"([^\"]*)\"$")
    public void i_would_like_to_delete_my_order_by_if_I_am_not_not_happy_with_it(String orderId) throws FileNotFoundException {
        storeAPISteps.deleteOrderInfoById(StoreStepDefn.storeUrl,orderId);
    }

}
