package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
//import org.testng.annotations.BeforeClass;

import static constants.Constants.Actions.*;
import static constants.Constants.Path.AddEvent_PATH;
import static constants.Constants.Path.WhpPUSH_PATH;
import static constants.Constants.Servers.Base_URL;
import static constants.Constants.Servers.WhpPUSH_URL;
import static constants.Constants.Variables.*;
import static org.hamcrest.Matchers.equalTo;

public class TestConfig {

    protected RequestSpecification requestSpecificationAdd = new RequestSpecBuilder()
            .addHeader("sid",Sid)
            .setBaseUri(Base_URL+AddEvent_PATH+AddEvent_ADD)
            .build();

    protected RequestSpecification requestSpecificationModify = new RequestSpecBuilder()
            //.addHeader("sid",Sid)
            .setBaseUri(Base_URL+ModifyEvent+Sid)
            .build();



    protected RequestSpecification requestSpecificationWhppushVBR = new RequestSpecBuilder()
            //.addHeader("sid",Sid)
            .setBaseUri(WhpPUSH_URL+WhpPUSH_PATH)
            .build();

    protected ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(StatusCode)
            .build();

    protected ResponseSpecification responseSpecificationWhppushVBR = new ResponseSpecBuilder()
            .expectBody("result", equalTo("ok"))
            .build();



    public String WhppushVBRbody="{\n" +
            "\"status\":\""+status+"\",\n" +
            "\"channel\":\""+Ch_VBR+"\",\n" +
            "\"timestamp\":\"15-06-2022 21:16:47\",\n" +
            "\"order_id\":\""+ref1+"\"\n" +
            "}";





    @BeforeClass

    public void SetUp(){
        RestAssured.baseURI = server;
        RestAssured.basePath = path;
    }
}
