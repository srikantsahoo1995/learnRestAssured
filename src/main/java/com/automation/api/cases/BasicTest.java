package com.automation.api.cases;


import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import com.automation.api.testmodels.Location;
import com.automation.api.testmodels.PlaceModel;
import com.automation.api.testmodels.PlaceResponse;
import org.json.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class BasicTest {


    @Test
    public void testBody() throws IOException {
        System.out.println("We are good to go.");
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        // Create POJO Object for Request Body
        Location location = new Location(-38.383494, 33.427362);
        PlaceModel place = new PlaceModel(
                location,
                50,
                "Rahul Shetty Academy",
                "(+91) 983 893 3937",
                "29, side layout, cohen 09",
                Arrays.asList("shoe park", "shop"),
                "http://rahulshettyacademy.com",
                "French-IN"
        );


        // POST call
        ExtractableResponse<Response> response = given().log().all().queryParam("key", "qaclick123")
                .header("Accept", "application/json")
                .body(place)
                .post("/maps/api/place/add/json")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)")
                .extract();



        //Response Validation 1
       String placeID =  response.body().jsonPath().get("place_id").toString()
        ;
        System.out.println("The place ID is : "+ placeID);

        //Response Validation using JSON path

        JsonPath js = new JsonPath(response.response().asString());
        placeID = js.get("place_id").toString();
        System.out.println("The place ID is : "+ placeID);

        String jsonString = response.response().asString();

        //Response Validation using JSON Object
        JSONObject jsonObject = new JSONObject(jsonString);
        placeID = jsonObject.getString("place_id");
        System.out.println("The place ID is : "+ placeID);

        //Response validation with POJO class

        // Deserialize response to POJO
        PlaceResponse placeResponse = response.as(PlaceResponse.class);

        // Print details
        System.out.println("Pojo class Status: " + placeResponse.getStatus());
        System.out.println("POJO class Place ID: " + placeResponse.getPlace_id());

        //GET Call

        Response getCallResponse = given()
                .log().all()
                .queryParam("key","qaclick123")
                .queryParam("place_id", placeID)
                .header("Accept", "application/json")
                .get("/maps/api/place/get/json")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().response();

        System.out.println(getCallResponse.getBody().jsonPath().get("name").toString());

        /*PlaceModel getResponse = getCallResponse.as(PlaceModel.class);
        System.out.println("GET Call place name :"+ getResponse.getName());
        System.out.println("GET Call lang name :"+ getResponse.getLocation().getLat());*/


        // POST call with external file contains payload
         response = given().log().all().queryParam("key", "qaclick123")
                .header("Accept", "application/json")
                .body(new String(Files.readAllBytes(Path.of("F:\\Learning2025\\place_payload.json"))))
                 .when()
                .post("/maps/api/place/add/json")
                .then().log().all()
                .assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)")
                .extract();



        //Response Validation 1
        String placeID1 =  response.body().jsonPath().get("place_id").toString()
                ;
        System.out.println("The place ID is : "+ placeID1);



    }
}