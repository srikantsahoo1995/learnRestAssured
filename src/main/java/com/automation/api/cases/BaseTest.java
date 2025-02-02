package com.automation.api.cases;

import com.automation.api.propconfigs.Configuration;
import com.automation.api.restclient.RestClient;
import org.testng.annotations.BeforeClass;


public class BaseTest {

    protected RestClient restClient;
    protected Configuration configuration;


    @BeforeClass
    public void setup() {

        configuration = new Configuration();
        configuration.initProp();






    }
}
