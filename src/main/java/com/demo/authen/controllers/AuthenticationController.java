package com.demo.authen.controllers;

import com.demo.authen.auth.AuthenticationRequest;
import com.demo.authen.auth.AuthenticationResponse;
import com.demo.authen.dto.KeyCloakResponse;
import com.demo.authen.dto.LoginRequest;
import com.demo.authen.dto.RegisterResponse;
import com.demo.authen.services.AuthenticationService;
import com.demo.authen.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody CreateUserRequest request) {
        log.info("into register of AuthenticationController");
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("into authenticate of AuthenticationController");
        return ResponseEntity.ok(service.authenticate(request));
    }




}
