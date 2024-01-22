package com.rgq.f1challenge.service;

import com.rgq.f1challenge.controller.filter.AuthFilter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ConfigurationService {
    private final ErrorService errorService;

    @Value("${f1challenge.auth.active}")
    private boolean authActive;
    @Value("${f1challenge.auth.username}")
    private String username;
    @Value("${f1challenge.auth.password}")
    private String password;
    @Value("${f1challenge.track.url}")
    private String trackUrl;
    @Value("${f1challenge.driver.avatar}")
    private String diverAvatar;

    public ConfigurationService(ErrorService errorService) {
        this.errorService = errorService;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter(this, errorService));
        registrationBean.addUrlPatterns("/private/*", "/api/*");
        return registrationBean;
    }
}
