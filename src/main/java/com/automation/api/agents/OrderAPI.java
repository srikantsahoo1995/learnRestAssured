package com.automation.api.agents;

import com.automation.api.propconfigs.Configuration;
import com.automation.api.restclient.RestClient;
import com.automation.api.testmodels.OrdersRequest;
import com.automation.api.testmodels.OrdersResponse;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class OrderAPI extends RestClient {

    private final String CREATE_ORDER_ENDPOINT = "/api/ecom/order/create-order";
    private final String VIEW_PRODUCT_ENDPOINT = "/api/ecom/order/get-orders-details";


    public OrderAPI(Configuration configuration){
        super(configuration);
    }



    public String createOrder(OrdersRequest ordersRequest){

        Response response =postCall(CREATE_ORDER_ENDPOINT,ordersRequest, null,null, "json");

        OrdersResponse ordersResponse = response.getBody().as(OrdersResponse.class);
        return ordersResponse.getOrders().get(0);
    }

    public void viewOrder(String orderID){
        Map<String,Object>queryParam = new HashMap<>();
        queryParam.put("id",orderID);
        Response response = getCall(VIEW_PRODUCT_ENDPOINT,null,queryParam,"json");
        String obj = response.getBody().asString();
        JSONObject jsonObject = new JSONObject(obj);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        String actualOrderBy = dataObject.getString("orderBy");
        Assert.assertEquals(actualOrderBy,"ankitapupi@gmail.com");
    }
}
