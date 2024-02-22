package com.seikim.httpclient.resttempate;

import com.seikim.httpclient.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FetchByRestTemplateTest {
    @Autowired
    private FetchByRestTemplate restTemplate;

    @DisplayName("RestTemplate 조회에 성공한다.")
    @Test
    void webClientTest() {
        /* Given */
        /* When */
        Todo todo = restTemplate.fetchByTodo(1);

        /* Then */
        assertThat(todo).isNotNull();
    }

    @DisplayName("RestTemplate 여러 동기 조회에 성공한다.")
    @Test
    void todosTest() {
        /* Given */
        /* When */
        List<Todo> todos = restTemplate.fetchByTodos(1, 200);

        /* Then */
        assertThat(todos).hasSize(200);
    }
}