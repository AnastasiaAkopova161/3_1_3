package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    // CREATE
    void createUser(User user);

    // READ
    User getUserById(Long userId);

    User getUserByUsername(String username);

    // UPDATE
    boolean updateUser(User user);

    // DELETE
    boolean deleteUser(Long userId);

    // READ ALL
    List<User> getAllUsersWithRoles();
}
