package ru.itmentor.spring.boot_security.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MainController {

    @Operation(summary = "Get Main Page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Main Page retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Main Page not found")
    })
    @GetMapping({"/"})
    String getHomePage(Model model);

    @Operation(summary = "Logout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/logout")
    String logout(HttpServletRequest request, HttpServletResponse response);
}
