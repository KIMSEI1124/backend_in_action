package com.seikim.httpclient.webclient;

import com.seikim.httpclient.Todo;
import com.seikim.httpclient.stopwatch.EnableStopWatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
@Component
public class FetchByWebClient {
    public static final String URL = "https://jsonplaceholder.typicode.com/todos/";

    private final WebClient webClient;

    @EnableStopWatch
    public Todo fetchByTodo(int id) {
        Todo todo = webClient.get()
                .uri(URL + id)
                .retrieve()
                .bodyToMono(Todo.class)
                .block();
        log.info("fetch : \"{}\"", todo);
        return todo;
    }

    @EnableStopWatch
    public List<Todo> fetchByTodos(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .mapToObj(i -> webClient.get()
                        .uri(URL + i)
                        .retrieve()
                        .bodyToMono(Todo.class)
                        .block()
                ).toList();
    }

    @EnableStopWatch
    public List<Todo> fetchByTodosAsync(int start, int end) {
        return Flux.range(start, end)
                .flatMap(i -> webClient.get()
                        .uri(URL + i)
                        .retrieve()
                        .bodyToMono(Todo.class))
                .collectList()
                .block();
    }
}
