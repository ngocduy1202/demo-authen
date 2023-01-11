package com.demo.authen.controllers;
import com.demo.authen.auth.KeycloakProvider;
import com.demo.authen.model.User;
import com.demo.authen.repositories.UserRepository;
import com.demo.authen.services.KeyCloakService;
import com.demo.authen.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private UserRepository userRepo;
    private UserService userService;

    private final KeyCloakService kcService;

    private final KeycloakProvider kcProvider;

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> findByUserId(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/list")
    public List<User> findAllUsers() {
        List<User> users = userRepo.findAll();
        return users;
    }



}
