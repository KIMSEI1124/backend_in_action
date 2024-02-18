package com.seikim.dockerimagecompress;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class Controller {
    @GetMapping("/get")
    public ResponseEntity<Message> get() {
        Message message = new Message(String.format("""
                        Hello! Docker Image Compress
                        LocalDateTime.now (%s)
                        """,
                LocalDateTime.now()
        ));
        return ResponseEntity.ok(message);
    }
}
