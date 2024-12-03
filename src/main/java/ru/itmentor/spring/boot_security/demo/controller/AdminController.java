package ru.itmentor.spring.boot_security.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmentor.spring.boot_security.demo.model.User;

public interface AdminController {

    // GET Admin Page
    // отображение страницы администратора
    @Operation(summary = "Get Admin Page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Page retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User Page not found")
    })
    @GetMapping({"/admin", "/admin/", "/admin/list"})
    String adminPage(Model model);

    // CREATE
    // отображение формы добавления пользователя
    @Operation(summary = "Create a new User with Role(s) - Form")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @GetMapping("/admin/users/create")
    String newUser(Model model);

    // CREATE
    // сохранение (создание) пользователя
    @Operation(summary = "Save a User With Role(s) - Save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/admin/users/create")
    String saveUser(@ModelAttribute User user);

    // READ
    // отображение пользователя
    @Operation(summary = "Get User By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/admin/users/{id}")
    String getUser(Model model, @PathVariable("id") Long id);

    // UPDATE
    // отображение формы обновления пользователя
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/admin/users/update/{id}")
    String updateUserForm(@PathVariable("id") Long id, Model model);


    // UPDATE
    // обновление пользователя
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/admin/users/update")
    String updateUser(@ModelAttribute User user);

    // DELETE
    // удаление пользователя
    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/admin/users/delete/{id}")
    String deleteUser(@PathVariable("id") Long id, Model model);

    // GET ALL USERS
    // отображение всех пользователей
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found")
    })
    @GetMapping({"/admin/users", "/admin/users/", "/admin/users/list", "/admin/users/all"})
    String getAll(Model model, @RequestParam(value = "deleted", required = false) Boolean deleted);
}


