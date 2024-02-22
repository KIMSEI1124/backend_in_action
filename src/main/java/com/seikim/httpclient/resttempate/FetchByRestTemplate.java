package com.seikim.httpclient.resttempate;

import com.seikim.httpclient.Todo;
import com.seikim.httpclient.stopwatch.EnableStopWatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
@Component
public class FetchByRestTemplate {
    public static final String URL = "https://jsonplaceholder.typicode.com/todos/";
    private final RestTemplate restTemplate;

    @EnableStopWatch
    public Todo fetchByTodo(int id) {
        ResponseEntity<Todo> response = restTemplate.exchange(
                URL + id,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Todo.class
        );
        return response.getBody();
    }

    @EnableStopWatch
    public List<Todo> fetchByTodos(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .mapToObj(i -> restTemplate.exchange(
                                URL + i,
                                HttpMethod.GET,
                                HttpEntity.EMPTY,
                                Todo.class
                        ).getBody()
                ).toList();
    }
}
