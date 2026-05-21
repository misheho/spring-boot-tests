package com.misheho.spb_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/api/greeting")
   public Map<String, String> greeting() {
       return Map.of(
           "message", "Hello from the Spring Boot REST API!",
           "status", "ok"
       );
   }
}