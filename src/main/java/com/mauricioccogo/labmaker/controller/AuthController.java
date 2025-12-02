package com.mauricioccogo.labmaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/lista-req")
    public String list() {
        return "lista-req";
    }

    @GetMapping("/lista-usr")
    public String usuarios() {
        return "lista-usr";
    }
}

