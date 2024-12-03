package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@Service
@Transactional
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // CREATE
    @Transactional
    @Override
    public void createRole(Role role) {
        roleRepository.save(role);
    }
    // READ
    @Transactional(readOnly = true)
    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.getById(roleId);
    }
    // UPDATE
    @Transactional
    @Override
    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }
    // DELETE
    @Transactional
    @Override
    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }
    // READ ALL
    @Transactional(readOnly = true)
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

