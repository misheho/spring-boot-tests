package com.misheho.spb_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/api/greeting")
    public Map<String, String> greeting(@org.springframework.web.bind.annotation.RequestParam(value = "name", required = false) String name) {
        String greetingName = (name == null || name.isBlank()) ? "Spring Boot user" : name.trim();
        return Map.of(
            "message", String.format("Hello, %s from the Spring Boot REST API!", greetingName),
            "status", "ok"
        );
    }

    @PostMapping("/api/greeting")
    public Map<String, String> greetingPost(@RequestBody GreetingRequest request) {
        String greetingName = (request.getName() == null || request.getName().isBlank()) ? "Spring Boot user" : request.getName().trim();
        return Map.of(
            "message", String.format("Hello, %s from the Spring Boot REST API!", greetingName),
            "status", "ok"
        );
    }
}