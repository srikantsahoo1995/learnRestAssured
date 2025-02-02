package com.automation.api.restclient;

import com.automation.api.constants.APIHttpStatus;
import com.automation.api.propconfigs.Configuration;
import com.beust.ah.A;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class RestClient {
    private Configuration configuration;
    private RequestSpecBuilder specBuilder;
    private final String LOGIN_ENDPOINT = "/api/ecom/auth/login";
    private String baseURI;
    private String userName;
    private String password;
    private String accessToken;



    public RestClient(Configuration configuration) {
        specBuilder = new RequestSpecBuilder();
       this.configuration = configuration;
        baseURI= configuration.getProperty("baseUri");
        userName = configuration.getProperty("userName");
        password = configuration.getProperty("password");
        accessToken = generateAccessToken();
        specBuilder.setBaseUri(baseURI);
        specBuilder.addHeader("Authorization",accessToken);

    }


    private String generateAccessToken(){
        RestAssured.baseURI = baseURI;

        HashMap<String, String> loginPayload = new HashMap<String, String>();
        loginPayload.put("userEmail", userName);
        loginPayload.put("userPassword", password);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post(LOGIN_ENDPOINT);
        response.then()
                .assertThat()
                .statusCode(APIHttpStatus.OK_200.getStatus());
        String token =  response.getBody().jsonPath().getString("token");
        //JSONObject jsonObject = new JSONObject(response.getBody().asString());
        // token  = jsonObject.getString("token");

        return token;

    }

    private void setContentType(String contentType){

        if(contentType != null) {
            switch (contentType) {
                case "json":
                    specBuilder.setContentType(ContentType.JSON);
                    break;

                case "xml":
                    specBuilder.setContentType(ContentType.XML);
                    break;

                case "text":
                    specBuilder.setContentType(ContentType.TEXT);
                    break;
                default:
                    break;

            }
        }

    }


    private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, Object> queryParams, String contentType){
        setContentType(contentType);
        if(headersMap != null){
            specBuilder.addHeaders(headersMap);
        }
        if(queryParams!= null){
            specBuilder.addQueryParams(queryParams);
        }


        return specBuilder.build();
    }


    public Response postCall(String endPoint,Object requestBody, Map<String, String> headersMap, Map<String, Object> queryParams, String contentType){
        Response response = given(createRequestSpec(headersMap,queryParams,contentType))
                .log().all()
                .body(requestBody)
                .when()
                .post(endPoint)
                .then().assertThat().statusCode(APIHttpStatus.CREATED_201.getStatus())
                .extract().response();

        return response;
    }

    public Response postCall(String endPoint, Map<String, Object>formParmas, String multiPartKey, File value, Map<String, String> headersMap, Map<String, Object> queryParams, String contentType){
        Response response = given(createRequestSpec(headersMap,queryParams,contentType))
                .log().all()
                .formParams(formParmas)
                .multiPart(multiPartKey, value)
                .when()
                .post(endPoint)
                .then().log().all()
                .assertThat().statusCode(APIHttpStatus.CREATED_201.getStatus())
                .extract().response();

        return response;
    }


    public Response patchCall(String endPoint,Object requestBody, Map<String, String> headersMap, Map<String, Object> queryParams, String contentType){
        Response response = given(createRequestSpec(headersMap,queryParams,contentType))
                .log().all()
                .body(requestBody)
                .when()
                .patch(endPoint)
                .then().assertThat().statusCode(APIHttpStatus.OK_200.getStatus())
                .extract().response();

        return response;
    }


    public Response getCall(String endPoint, Map<String, String> headersMap, Map<String, Object> queryParams, String contentType){
        Response response = given(createRequestSpec(headersMap,queryParams,contentType))
                .log().all()
                .when()
                .get(endPoint)
                .then().assertThat().statusCode(APIHttpStatus.OK_200.getStatus())
                .extract().response();

        return response;
    }


    public Response deleteCall(String endPoint, Map<String, String> headersMap, Map<String, Object> queryParams, String contentType){
        Response response = given(createRequestSpec(headersMap,queryParams,contentType))
                .log().all()
                .when()
                .delete(endPoint)
                .then().assertThat().statusCode(APIHttpStatus.OK_200.getStatus())
                .extract().response();

        return response;
    }


















    @Test
    public  void testBody() {
        System.out.println("The Token is: " + accessToken);

        String body = "{\n" +
                "    \"orders\": [\n" +
                "        {\n" +
                "            \"country\": \"India\",\n" +
                "            \"productOrderedId\": \"679f3874e2b5443b1f4373f9\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        String endpoint = "/api/ecom/order/create-order";

       Response response =  postCall(endpoint, body,null,null,"json");
       response.then().log().all().assertThat().statusCode(APIHttpStatus.CREATED_201.getStatus());
    }




}
