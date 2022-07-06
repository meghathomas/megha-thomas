package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class RequestBuilder {

    private RequestSpecification requestSpec = null;

    public RequestBuilder(String urlPath,String contentType,Object jsonObject) throws FileNotFoundException {

        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBasePath(urlPath);
        builder.setContentType(contentType);
        if(jsonObject != null) {
            builder.setBody(jsonObject);
        }
        System.out.println(urlPath);
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();

    }

    public RequestBuilder(String urlPath,String filePath) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBasePath(urlPath);
        builder.setContentType("multipart/form-data");
        builder.addMultiPart("file",filePath,"image/jpeg");
        System.out.println(urlPath);
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
    }

    public  RequestSpecification getRequestSpecification() {
        return requestSpec;
    }

}
