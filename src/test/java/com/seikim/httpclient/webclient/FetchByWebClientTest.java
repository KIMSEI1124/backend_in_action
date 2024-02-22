package com.seikim.httpclient.webclient;

import com.seikim.httpclient.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FetchByWebClientTest {
    @Autowired
    private FetchByWebClient webClient;

    @DisplayName("WebClient 조회에 성공한다.")
    @Test
    void webClientTest() {
        /* Given */
        /* When */
        Todo todo = webClient.fetchByTodo(1);

        /* Then */
        assertThat(todo).isNotNull();
    }

    @DisplayName("WebClient 여러 동기 조회에 성공한다.")
    @Test
    void todosTest() {
        /* Given */
        /* When */
        List<Todo> todos = webClient.fetchByTodos(1, 200);

        /* Then */
        assertThat(todos).hasSize(200);
    }

    @DisplayName("WebClient 여러 비동기 조회에 성공한다.")
    @Test
    void todosAsyncTest() {
        /* Given */
        /* When */
        List<Todo> todos = webClient.fetchByTodosAsync(1, 200);
        System.out.println(todos);
        /* Then */
        assertThat(todos).hasSize(200);
    }
}