package com.automation.api.cases;

import com.automation.api.agents.OrderAPI;
import com.automation.api.agents.ProductAPI;
import com.automation.api.constants.APIHttpStatus;
import com.automation.api.restclient.RestClient;
import com.automation.api.testmodels.AddProductPayload;
import com.automation.api.testmodels.OrdersRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class EcomE2ETest extends BaseTest {

    String add_Product_endpoint = "/api/ecom/product/add-product";






    @Test
    public  void testBody() {

        AddProductPayload  addProductPayload = new
                AddProductPayload("Levis jeans", "fashion", "pant",
                "11500","Levis  jeans", "men",new File("src\\test\\resources\\download.jpg"));



        ProductAPI productAPI = new ProductAPI(configuration);
        String productID = productAPI.addProduct(addProductPayload);
        System.out.println("The Product ID is : "+ productID);

        OrdersRequest.Order order = new OrdersRequest.Order();
        order.setCountry("India");
        order.setProductOrderedId(productID);


        OrdersRequest ordersRequest = new OrdersRequest();
        ordersRequest.setOrders(Collections.singletonList(order));

        OrderAPI orderAPI = new OrderAPI(configuration);
        String orderID = orderAPI.createOrder(ordersRequest);

        System.out.println("The order ID is : "+ orderID);

        orderAPI.viewOrder(orderID);


        productAPI.deleteProduct(productID);

    }


}
