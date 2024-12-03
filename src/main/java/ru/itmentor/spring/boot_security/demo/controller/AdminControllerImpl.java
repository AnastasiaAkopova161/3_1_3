package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminControllerImpl implements AdminController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminControllerImpl(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private User getCurrentUser() {
        Authentication authentication = getAuthentication();
        String username = authentication.getName();
        return userService.getUserByUsername(username);
    }

    // GET Admin Page
    // отображение страницы администратора
    @Override
    @GetMapping({"", "/", "/list"})
    public String adminPage(Model model) {
        model.addAttribute("user", getCurrentUser());
        // Логика для отображения страницы администратора
        return "admin/admin";
    }

    // CREATE
    // отображение формы добавления пользователя
    @Override
    @GetMapping("/users/create")
    public String newUser(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "admin/createUser";
    }

    // CREATE
    // сохранение (создание) пользователя
    @Override
    @PostMapping("/users/create")
    public String saveUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/admin/users";
    }

    // READ
    // отображение пользователя
    @Override
    @GetMapping("/users/details/{id}")
    public String getUser(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/readUser";
    }

    // UPDATE
    // отображение формы обновления пользователя
    @Override
    @GetMapping("/users/update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "admin/updateUser";
    }

    // UPDATE
    // обновление пользователя
    @Override
    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    // DELETE
    // удаление пользователя
    @Override
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        boolean deleted = userService.deleteUser(id);
        model.addAttribute("deleted", deleted);
        return "redirect:/admin/users?deleted=" + deleted;
    }

    // GET ALL USERS
    // отображение всех пользователей
    @Override
    @GetMapping({"/users", "/users/", "/users/list", "/users/all"})
    public String getAll(Model model, @RequestParam(value = "deleted", required = false) Boolean deleted) {
        List<User> users = userService.getAllUsersWithRoles();
        model.addAttribute("users", users);
        model.addAttribute("deleted", deleted != null && deleted);
        return "admin/listUsers";
    }
}


