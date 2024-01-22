package com.f1manager.controller.filter;

import com.f1manager.model.dto.UserDto;
import com.f1manager.model.exception.UnauthorizedException;
import com.f1manager.service.ConfigurationService;
import com.f1manager.service.ErrorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public class AuthFilter implements Filter {
    private final ConfigurationService configurationService;
    private final ErrorService errorService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            if (configurationService.isAuthActive()) {
                UserDto user = getUser((HttpServletRequest) servletRequest);
                if (invalidCredentials(user)) {
                    throw new UnauthorizedException(user);
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            errorService.handle(e, (HttpServletResponse) servletResponse);
        }
    }

    private UserDto getUser(HttpServletRequest request) throws JsonProcessingException, UnauthorizedException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> optional = Arrays
                .stream(cookies)
                .filter(cookie -> cookie.getName().equals("auth"))
                .findFirst();
            if (optional.isPresent()) {
                return new ObjectMapper().readValue(
                    URLDecoder.decode(optional.get().getValue(), StandardCharsets.UTF_8),
                    UserDto.class
                );
            }
        }
        throw new UnauthorizedException("No auth cookie found");
    }

    private boolean invalidCredentials(UserDto user) {
        return !user.getUsername().equals(configurationService.getUsername()) ||
            !user.getPassword().equals(configurationService.getPassword());
    }
}
