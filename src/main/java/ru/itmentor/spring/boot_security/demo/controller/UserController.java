package ru.itmentor.spring.boot_security.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public interface UserController {
    @Operation(summary = "Get User Page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Page retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User Page not found")
    })
    @GetMapping({"/user", "/user/", "/user/list"})
    String getUserPage(Model model);
}
