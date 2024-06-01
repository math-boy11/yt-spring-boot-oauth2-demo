package com.mathboy11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                //Allow anonymous access to the root page
                .requestMatchers("/", "/css/*", "/js/*", "/images/**", "/oauth/**").permitAll()
                //Authenticate all other requests
                .anyRequest().authenticated());

        http.oauth2Login((form) -> form
                .successHandler(loginSuccessHandler)
                .permitAll()
        );

        return http.build();
    }
}