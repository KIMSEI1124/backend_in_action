package com.seikim.customproperties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomPropertiesTest {
    @Autowired
    private CustomProperties customProperties;

    @DisplayName("기본 값을 정상적으로 불러온다.")
    @Test
    void getDefaultValueTest() {
        /* Given */
        /* When */
        int number = customProperties.getNumber();

        /* Then */
        assertThat(number).isEqualTo(100);
    }

    @DisplayName("바인딩 된 값을 정상적으로 불러온다.")
    @Test
    void getBindingValueTest() {
        /* Given */
        /* When */
        String string = customProperties.getString();

        /* Then */
        assertThat(string).isEqualTo("Properties");
    }
}