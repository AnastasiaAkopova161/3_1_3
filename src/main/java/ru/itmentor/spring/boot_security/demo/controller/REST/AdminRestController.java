package ru.itmentor.spring.boot_security.demo.controller.REST;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService) {
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

    // GET
    // получение данных о пользователе на http://localhost:8080/api/user
    @GetMapping({"", "/", "/list", "/me", "/profile", "/current"})
    @ResponseBody
    public ResponseEntity<User> getCurrentUserProfile() {
        final User user = getCurrentUser();
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // CREATE
    // создание пользователя - http://localhost:8080/api/admin/users/create
    @PostMapping("/users/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.createUser(user);
        User createdUser = userService.getUserByUsername(user.getUsername());
        return createdUser != null
                ? new ResponseEntity<>(createdUser, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // READ
    // получение информации о пользователе - http://localhost:8080/api/admin/users/{id}
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        final User user = userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // UPDATE
    // обновление пользователя - http://localhost:8080/api/admin/users/update/{id}
    @PutMapping("/users/update/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable("id") Long id, @RequestBody User tempUser) {
        User user = userService.getUserById(id); // получаем пользователя по id
        boolean updated = false; // по умолчанию пользователь не обновлен
        if (user != null) {
            // Обновляем данные пользователя из тела запроса
            user.setFirstName(tempUser.getFirstName());
            user.setLastName(tempUser.getLastName());
            user.setEmail(tempUser.getEmail());
            user.setUsername(tempUser.getUsername());
            user.setPassword(tempUser.getPassword());
            user.setExpirationDate(tempUser.getExpirationDate());
            user.setLocked(tempUser.isLocked());
            user.setRoles(tempUser.getRoles());
            // Сохраняем обновленного пользователя в БД
            updated = userService.updateUser(user);
        }
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    // DELETE
    // удаление пользователя - http://localhost:8080/api/admin/users/delete/{id}
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id) {
        final boolean deleted = userService.deleteUser(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    // LIST ALL USERS - http://localhost:8080/api/admin/users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        final List<User> users = userService.getAllUsersWithRoles();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
