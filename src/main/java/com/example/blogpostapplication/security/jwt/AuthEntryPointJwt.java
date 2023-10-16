package com.example.blogpostapplication.security.jwt;

import com.example.blogpostapplication.model.exceptions.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  private final ObjectMapper objectMapper;

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");

    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.UNAUTHORIZED, "User needs to be logged in.");

    try (var outputStream = response.getOutputStream()) {
      this.objectMapper.writeValue(outputStream, errorResponse);
    } catch (AuthenticationException authenticationException) {
      logger.error("Unauthorized error : {}", authenticationException.getMessage());
    }
  }
}
