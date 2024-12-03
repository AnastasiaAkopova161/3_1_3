package ru.itmentor.spring.boot_security.demo.controller.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    // POST
    // Аутентификация пользователя - http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        // Создаем и заполняем тело ответа
        LoginResponse response = new LoginResponse();
        try {
            // выполняет процесс аутентификации пользователя
            Authentication authentication = authenticationManager.authenticate(
                    // создает аутентификационные данные пользователя
                    new UsernamePasswordAuthenticationToken(username, password));
            // устанавливает аутентификацию в контексте безопасности
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // вернем в JSON-е данные пользователя
            User user = userService.getUserByUsername(username);
            response.setMessage("Login successful");
            response.setUser(user);
            // Возвращаем ответ в JSON-е
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            // Ошибка аутентификации
            response.setMessage("Authentication failed");
            response.setError(e.getMessage());
            // Возвращаем ответ в JSON-е
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
    // LOGOUT
    // Выход пользователя - http://localhost:8080/api/auth/logout
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Создаем и заполняем тело ответа
        Map<String, String> response = new HashMap<>();
        if (authentication != null) {
            SecurityContextHolder.clearContext(); // Очищаем контекст безопасности
        }
        response.put("message", "Logout successful");
        // Возвращаем ответ в JSON-е
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
