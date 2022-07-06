package stepDefinitions.pet;

import base.Utils;
import common.pet.PetAPI;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import pojo.pet.PetAPIResponse;
import pojo.pet.PetInfo;
import pojo.store.StoreInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PetStepDefn {

    private Response res = null;
    private static String petUrl = null;
    private PetInfo petInfo = null;
    PetAPI petAPISteps=new PetAPI();

    @Before
    public void setup() throws IOException {

        RestAssured.baseURI = Utils.getDataFromPropertiesFile("baseURL");;
    }

    @After
    public void tearDown() {

        RestAssured.reset();
    }

    @Given("^Create new pet information and validate$")
    public void create_new_pet_information_and_validate(List<Map<String, String>> listOfData) throws FileNotFoundException {
        Map<String, String> petData = listOfData.get(0);
        petInfo = petAPISteps.createPetClass(petData);
        res = petAPISteps.createPetRequest("/pet", petInfo);
        PetInfo petResponse = petAPISteps.validatePetInfoIsAdded(res);
        petAPISteps.comparePetInfo(petInfo, petResponse);
    }

    @When("^Update the Pet info with below data and Validate$")
    public void update_the_pet_info_with_below_data_and_validtae(List<Map<String, String>> listOfData) throws FileNotFoundException {
        Map<String, String> petDataToBeUpdated = listOfData.get(0);
        PetInfo toBeUpdated = petAPISteps.createPetClass(petDataToBeUpdated);
        PetInfo actualResponse = petAPISteps.updatePetRequest("/pet", toBeUpdated);
        petAPISteps.comparePetInfo(toBeUpdated, actualResponse);
    }

    @Then("^Fetch pet info by status and validate if updated pet with \"([^\"]*)\" exists for \"([^\"]*)\"$")
    public void i_can_view_pet_info_by_status_and_validate_if_updated_pet_profile_exists_for(String petStatus,int petId) throws FileNotFoundException {
        PetInfo[] petResponse = petAPISteps
                .findPetInfoByStatus("/pet/findByStatus?status=" + petStatus);
        for (PetInfo petProfile : petResponse) {
            if (petProfile.getId().equals(petId)){
                Assert.assertEquals("Verify Pet Status!", petProfile.getStatus(), petStatus);
            }
        }

    }

    @When("^Fetch pet info with pet ID \"([^\"]*)\"$")
    public void i_add_new_pet_it_shoud_be_avilable_to_serach_with_pet_by_ID(String petId) throws FileNotFoundException {
        PetInfo petResponse = petAPISteps.fetchPetInfoById("/pet", petId);
        PetInfo actualResponse = petAPISteps.validatePostStatusAndReturnResponse(res);
        Assert.assertEquals("Verify Pet Id!", actualResponse.getId().toString(), petId);
        Assert.assertEquals("Status Check Passed!", 200, res.getStatusCode());
    }

    @Then("^Upload a pet image \"([^\"]*)\" by \"([^\"]*)\"$")
    public void upload_a_pet_image_using(String image, String petId) {
        String dir = System.getProperty("user.dir");
        PetAPIResponse expectedResponse = petAPISteps.uploadImageOfPetById(dir + image, "/pet",
                petId);
        Assert.assertEquals("Status Check Passed!", "200", expectedResponse.getCode().toString());
        Assert.assertNotNull("type field in response is not empty", expectedResponse.getType());
        Assert.assertNotNull("Message field in response is not empty", expectedResponse.getMessage());
    }

    @Then("^Delete the pet by id \"([^\"]*)\"$")
    public void i_can_delete_the_pet_profile_by_id(String petId) throws FileNotFoundException {
        PetAPIResponse expectedResponse = petAPISteps.deletePetInfoById("/pet", petId);
        Assert.assertEquals("Status Check Passed!", "200", expectedResponse.getCode().toString());
        Assert.assertNotNull("type field in response is not empty", expectedResponse.getType());
        Assert.assertEquals("Message return id", petId, expectedResponse.getMessage());
    }

}
