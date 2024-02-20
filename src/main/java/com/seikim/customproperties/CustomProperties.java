package com.seikim.customproperties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "custom.data")
public class CustomProperties {
    private int number = 100;
    private String string;
}
