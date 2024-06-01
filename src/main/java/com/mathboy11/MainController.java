package com.mathboy11;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MainController {
    @GetMapping("/")
    public void getLoginPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }

    @GetMapping("/welcome")
    public String getWelcomePage(HttpServletRequest req) {
        String name = (String) req.getSession().getAttribute("name");
        String email = (String) req.getSession().getAttribute("email");
        String profile = (String) req.getSession().getAttribute("profile");

        return String.format("""
            <h1>Welcome %s!</h1>
            <p>Email: %s</p>
            <img src="%s"></img><br>
            <a href="/logout">Logout</a>
        """, name, email, profile);
    }
}
