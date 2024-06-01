package com.mathboy11;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    public LoginSuccessHandler() {
        super.setDefaultTargetUrl("/welcome");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2User userProfile = (OAuth2User) authentication.getPrincipal();

        req.getSession().setAttribute("loggedin", true);
        req.getSession().setAttribute("name", userProfile.getAttributes().get("name"));
        req.getSession().setAttribute("email", userProfile.getAttributes().get("email"));
        req.getSession().setAttribute("profile", userProfile.getAttributes().get("picture"));

        super.onAuthenticationSuccess(req, response, authentication);
    }
}