package ru.itmentor.spring.boot_security.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.model.ErrorResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler, AuthenticationEntryPoint {

    // обработка ошибки "403 (Forbidden)"
    // если пользователь не имеет соответствующих прав
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("403 (Forbidden)");
        // объект содержит информацию об ошибке
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "Access denied", accessDeniedException.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value()); // статус ответа
        response.setContentType("application/json"); // тип контента
        // пишем в тело ответа
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }

    // обработка ошибки "401 Unauthorized"
    // если пользователь не авторизован
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("401 Unauthorized");
        // объект содержит информацию об ошибке
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", authException.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // статус ответа
        response.setContentType("application/json"); // тип контента
        // пишем в тело ответа
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
