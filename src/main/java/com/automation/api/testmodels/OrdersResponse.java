package com.automation.api.testmodels;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {
    private List<String> orders;
    private List<String> productOrderId;
    private String message;
}

