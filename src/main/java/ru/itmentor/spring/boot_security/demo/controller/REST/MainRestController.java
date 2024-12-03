package ru.itmentor.spring.boot_security.demo.controller.REST;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@PreAuthorize("permitAll()")
public class MainRestController {

    // GET API home page data in JSON
    // http://localhost:8080/api или http://localhost:8080/api/index или http://localhost:8080/api/main
    @GetMapping({"","/", "/index", "/main"})
    public ResponseEntity<Map<String, Object>> getHomePage() {
        Map<String, Object> response = new HashMap<>(); // ключ-значение для JSON
        response.put("description", "This is a sample API for managing users.");
        response.put("version", "1.0.0");
        return ResponseEntity.ok().body(response); // отправили ответ
    }
}