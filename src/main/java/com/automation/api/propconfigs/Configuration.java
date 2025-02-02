package com.automation.api.propconfigs;

import java.io.FileInputStream;
import java.util.Properties;

public class Configuration {

    private Properties prop;
    private FileInputStream fileInput;
    private final String filePath = "src\\main\\resources\\config.properties";

    public Properties initProp(){

        if(prop == null){
            prop = new Properties();

            try {
                fileInput = new FileInputStream(filePath);
                prop.load(fileInput);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return prop;
    }


    public String getProperty(String key) {
        initProp();
        return prop.getProperty(key);
    }

}
