package ru.itmentor.spring.boot_security.demo.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int status;
    private String message;
    private String details;

    public ErrorResponse(HttpStatus httpStatus, String internalServerError, String message) {
        this.status = httpStatus.value();
        this.message = message;
        this.details = internalServerError;
    }

    // Геттеры и сеттеры для полей

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}






