package com.sparta.magazine_week2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/user/register")
    public String registerForm() {
        return "signup";
    }

    @GetMapping("/user/loginView")
    public String loginForm() {
        return "login";
    }
}
