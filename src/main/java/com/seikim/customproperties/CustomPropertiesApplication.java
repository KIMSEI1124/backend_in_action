package com.seikim.customproperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan("com.seikim.customproperties")
@SpringBootApplication
public class CustomPropertiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomPropertiesApplication.class, args);
    }

}
