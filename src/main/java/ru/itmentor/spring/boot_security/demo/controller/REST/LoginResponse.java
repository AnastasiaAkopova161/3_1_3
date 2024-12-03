package ru.itmentor.spring.boot_security.demo.controller.REST;


import ru.itmentor.spring.boot_security.demo.model.User;

public class LoginResponse {
    private String message;
    private User user;
    private String error;

    // Конструкторы, геттеры и сеттеры

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

