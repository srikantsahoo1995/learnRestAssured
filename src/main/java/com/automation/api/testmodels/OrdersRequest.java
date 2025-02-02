package com.automation.api.testmodels;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdersRequest {
    private List<Order> orders;  // List of inner class objects

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Order {
        private String country;
        private String productOrderedId;
    }
}

