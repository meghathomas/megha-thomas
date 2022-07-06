package common.pet;

import base.RequestBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import pojo.pet.Category;
import pojo.pet.PetAPIResponse;
import pojo.pet.PetInfo;
import pojo.pet.Tag;
import pojo.store.StoreInfo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PetAPI {

    public PetInfo createPetClass(Map<String, String> petData) {
        Category category = new Category(Integer.parseInt(petData.get("categoryId")),petData.get("categoryName"));
        List<String> photoUrls = new ArrayList<>();
        photoUrls.add(petData.get("photoUrls"));
        List<Tag> tags = new ArrayList<>();
        Tag tagData = new Tag(Integer.parseInt(petData.get("tagsId")),petData.get("tagsName"));
        tags.add(tagData);
        PetInfo petInfo = new PetInfo(Integer.parseInt(petData.get("petId")),category,petData.get("petName"),photoUrls,tags,petData.get("status"));
        return petInfo;
    }

    public Response createPetRequest(String url, PetInfo petInfo) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder(url, "application/json", petInfo);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().post();
        return res;
    }

    private PetInfo petResponseDeSerialization(Response res) {
        return res.as(PetInfo.class);
    }

    public PetInfo validatePetInfoIsAdded(Response res) {
        PetInfo petInfo = petResponseDeSerialization(res);
        Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
        return petInfo;
    }

    public void comparePetInfo(PetInfo expectedPetInfo, PetInfo actualPetResponse) {
        Assert.assertEquals("Validate Pet Id", expectedPetInfo.getId(), actualPetResponse.getId());
        Assert.assertEquals("Validate Pet Name", expectedPetInfo.getName(), actualPetResponse.getName());
        Assert.assertEquals("Validate Pet Status", expectedPetInfo.getStatus(), actualPetResponse.getStatus());
        Assert.assertEquals("Validate Category id", expectedPetInfo.getCategory().getId(), actualPetResponse.getCategory().getId());
        Assert.assertEquals("Validate Category Name", expectedPetInfo.getCategory().getName(), actualPetResponse.getCategory().getName());
        Assert.assertEquals("Validate Tag Id", expectedPetInfo.getTags().get(0).getId(), actualPetResponse.getTags().get(0).getId());
        Assert.assertEquals("Validate Tag Name", expectedPetInfo.getTags().get(0).getName(), actualPetResponse.getTags().get(0).getName());
        Assert.assertEquals("Validate Photo URL", expectedPetInfo.getPhotoUrls().get(0), actualPetResponse.getPhotoUrls().get(0));
    }

    public PetInfo updatePetRequest(String petUrl, PetInfo toBeUpdated) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder(petUrl,"application/json",toBeUpdated);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().put();
        PetInfo expectedResponse = res.as(PetInfo.class);
        Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
        return expectedResponse;
    }

    public PetInfo[] findPetInfoByStatus(String petUrl) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder(petUrl,"application/json",null);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().get();
        PetInfo[] expectedResponse = res.as(PetInfo[].class);
        Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
        return expectedResponse;
    }

    public PetInfo fetchPetInfoById(String url,String petId) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder(url + "/" + petId, "application/json", null);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().get();
        PetInfo expectedResponse = res.as(PetInfo.class);
        Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
        return expectedResponse;

    }

    public PetAPIResponse uploadImageOfPetById(String image, String petUrl, String petId) {
        RequestBuilder apiRequestBuilder = new RequestBuilder(petUrl + "/" + petId+"/uploadImage",image);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().post();
        Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
        return res.as(PetAPIResponse.class);
    }

    public PetAPIResponse deletePetInfoById(String petUrl, String petId) throws FileNotFoundException {
        RequestBuilder apiRequestBuilder = new RequestBuilder(petUrl + "/" + petId, "application/json", null);
        RequestSpecification requestSpec = apiRequestBuilder.getRequestSpecification();
        requestSpec = RestAssured.given().spec(requestSpec);
        Response res = requestSpec.when().delete();
        PetAPIResponse expectedResponse = res.as(PetAPIResponse.class);
        Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
        res = requestSpec.when().get();
        Assert.assertEquals("Verify pet record is deleted!", 404, res.getStatusCode());
        return expectedResponse;
    }

    public PetInfo validatePostStatusAndReturnResponse(Response res) {
        PetInfo petInfo = petResponseDeSerialization(res);
        Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
        return petInfo;
    }

}
