package com.automation.api.cases;

import com.automation.api.constants.APIHttpStatus;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostWithHashMap {
    public static void main(String[] args) {
        // Set Base URI
        RestAssured.baseURI = "https://rahulshettyacademy.com";  // Replace with actual API

        // Create HashMap for Request Body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("accuracy", 50);
        requestBody.put("name", "Rahul Shetty Academy");
        requestBody.put("phone_number", "(+91) 983 893 3937");
        requestBody.put("address", "29, side layout, cohen 09");
        requestBody.put("types", Arrays.asList("shoe park", "shop"));
        requestBody.put("website", "http://rahulshettyacademy.com");
        requestBody.put("language", "French-IN");

        // Create nested JSON object for "location"
        Map<String, Double> location = new HashMap<>();
        location.put("lat", -38.383494);
        location.put("lng", 33.427362);
        requestBody.put("location", location);

        // Send POST Request
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)  // Pass HashMap as body (RestAssured converts to JSON)
                .when()
                .post("/maps/api/place/add/json") // Replace with actual API endpoint
                .then()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getStatus())
                .extract()
                .response();



        Map respMap = response.as(Map.class);

        // Print Response
        System.out.println("Response: " + response.asString());

        System.out.println("Place ID is : "+ respMap.get("place_id"));
        System.out.println("Scope is : "+ respMap.get("scope"));
        System.out.println("Status is : "+ respMap.get("status"));
    }
}