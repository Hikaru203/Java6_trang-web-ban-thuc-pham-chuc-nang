package com.example.controller;

import com.example.entity.Account;
import com.example.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client/signin")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Account account) {
        Account registeredAccount = registrationService.registerAccount(account);
        if (registeredAccount != null) {
            return ResponseEntity.ok("Account registered successfully!");
        } else {
            return ResponseEntity.badRequest().body("Account registration failed.");
        }
    }
}
