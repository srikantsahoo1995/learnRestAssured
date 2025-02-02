package com.automation.api.agents;

import com.automation.api.propconfigs.Configuration;
import com.automation.api.restclient.RestClient;
import com.automation.api.testmodels.AddProductPayload;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProductAPI extends RestClient {

    private String ADD_PRODUCT_ENDPOINT = "/api/ecom/product/add-product";
    private String DELETE_PRODUCT_ENDPOINT = "/api/ecom/product/delete-product/%s";


    public ProductAPI(Configuration configuration){
        super(configuration);

    }

    public String addProduct (AddProductPayload payload){
        Map<String, Object> addProductForm =  new HashMap<>();
        addProductForm.put("productName", payload.getProductName());
        addProductForm.put("productCategory", payload.getProductCategory());
        addProductForm.put("productSubCategory", payload.getProductSubCategory());
        addProductForm.put("productPrice", payload.getProductPrice());
        addProductForm.put("productDescription", payload.getProductDescription());
        addProductForm.put("productFor", payload.getProductFor());
        addProductForm.put("productAddedBy", "663330a9a86f8f74dcd4fd56");

        Response response = postCall(ADD_PRODUCT_ENDPOINT, addProductForm,
                "productImage", payload.getProductImage(), null, null,null);

       return response.getBody().jsonPath().get("productId");

    }


    public void deleteProduct(String productID){
        deleteCall(String.format(DELETE_PRODUCT_ENDPOINT,productID),null,null,null);
    }



}
