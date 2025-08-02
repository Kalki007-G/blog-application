package com.kalki.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/test")
    public String test() {
        String rawPassword = "xyz";
        String storedHash = "$2a$10$ozGgkp0mic1n.eT0MmyxnuSOUfyBTMwSYB/sMddWPeul.vDx30ehi";

        if (passwordEncoder.matches(rawPassword, storedHash)) {
            return "MATCH";
        } else {
            return "NO MATCH";
        }
    }
}
