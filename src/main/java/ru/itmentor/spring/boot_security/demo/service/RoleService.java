package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    // CREATE
    void createRole(Role role);
    // READ
    Role getRoleById(Long roleId);
    // UPDATE
    Role updateRole(Role role);
    // DELETE
    void deleteRole(Long roleId);
    // READ ALL
    List<Role> getAllRoles();
}


