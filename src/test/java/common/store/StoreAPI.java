package common.store;

import base.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import pojo.store.StoreInfo;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Map;

public class StoreAPI {

    public Response fetchInventoryData(String inventoryURL) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder(inventoryURL, "application/json", null);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().get();
        return res;
    }

    public StoreInfo createStoreClass(Map<String,String> strStoreData) {
        StoreInfo storeInfo = new StoreInfo(Integer.parseInt(strStoreData.get("orderId")),
                Integer.parseInt(strStoreData.get("petId")), Integer.parseInt(strStoreData.get("quantity")), strStoreData.get("shipdate"),
                strStoreData.get("status"), Boolean.parseBoolean(strStoreData.get("complete")));
        return storeInfo;
    }

    public Response createOrderRequest(String url, StoreInfo storeInfo) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder(url, "application/json", storeInfo);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().post();
        return res;
    }

    public StoreInfo validatePostStatusAndReturnResponse(Response res) {
        StoreInfo storeInfo = storeResponseDeSerialization(res);
        Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
        return storeInfo;
    }

    private StoreInfo storeResponseDeSerialization(Response res) {
        return res.as(StoreInfo.class);
    }

    public String strDateFormat(String date, String format, boolean isUTCTime) {
        String returnDate = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            if (isUTCTime) {
                dateFormat.setTimeZone(TimeZone.GMT_ZONE);
            }
            returnDate = dateFormat.parse(date).toString();

        } catch (ParseException exception) {
            System.out.println("Failed to parse date > " + exception.getMessage());
        }
        return returnDate;
    }

    public void compareStoreInfo(StoreInfo expected, StoreInfo actual) {
        Assert.assertEquals("Validate order Id", expected.getId(), actual.getId());
        Assert.assertEquals("Validate pet Id", expected.getPetId(), actual.getPetId());
        Assert.assertEquals("Validate quantity", expected.getQuantity(), actual.getQuantity());
        String expectedShippedDate = strDateFormat(expected.getShipDate(), "yyyy-MM-dd'T'HH:mm",false);
        String actualShippedDate = strDateFormat(actual.getShipDate(), "yyyy-MM-dd'T'HH:mm",true);
        Assert.assertEquals("Validate shipped date", expectedShippedDate, actualShippedDate);
        Assert.assertEquals("Validate status", expected.getStatus(), actual.getStatus());
        Assert.assertEquals("Validate complete field", expected.getComplete(), actual.getComplete());
    }

    public StoreInfo fetchOrderInfoById(String orderUrl, String orderId) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder(orderUrl + "/" + orderId, "application/json", null);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().get();
        StoreInfo expectedResponse = res.as(StoreInfo.class);
        Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
        return expectedResponse;
    }

    public void deleteOrderInfoById(String orderUrl, String orderId) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder(orderUrl + "/" + orderId, "application/json", null);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().delete();
        Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
        res = requestSpec.when().get();
        Assert.assertEquals("Validate order removed!", 404, res.getStatusCode());
    }

}
