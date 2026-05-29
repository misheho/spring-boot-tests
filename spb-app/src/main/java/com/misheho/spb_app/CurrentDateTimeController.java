package com.misheho.spb_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrentDateTimeController {

    @GetMapping("/datetime")
    public Map<String, String> currentDateTime() {
        return Map.of("dateTime", OffsetDateTime.now().toString());
    }
}
