package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    @Transactional
    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }
    // READ
    // by id
    @Transactional(readOnly = true)
    @Override
    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(user -> {
            user.getRoles().size(); // принудительно загружаем роли пользователя, вместо ленивой загрузки
            return user;
        }).orElse(null);
    }
    // by username
    @Transactional(readOnly = true)
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    // UPDATE
    @Transactional
    @Override
    public boolean updateUser(User user) {
        userRepository.save(user);
        return true;
    }
    // DELETE
    @Transactional
    @Override
    public boolean deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return false;
    }
    // READ ALL
    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsersWithRoles() {
        return userRepository.findAllWithRoles();
    }
}

