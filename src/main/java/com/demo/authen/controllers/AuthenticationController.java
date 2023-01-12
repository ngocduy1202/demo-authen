package com.demo.authen.controllers;

import com.demo.authen.auth.AuthenticationRequest;
import com.demo.authen.auth.AuthenticationResponse;
import com.demo.authen.config.KeycloakProvider;

import com.demo.authen.dto.RegisterResponse;
import com.demo.authen.services.AuthenticationService;
import com.demo.authen.dto.CreateUserRequest;
import com.demo.authen.services.KeyCloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;

    private final KeycloakProvider kcProvider;

    private final KeyCloakService kcService;

//    @PostMapping("/register")
//    public ResponseEntity<RegisterResponse> register(@RequestBody CreateUserRequest request) {
//        log.info("into register of AuthenticationController");
//        return ResponseEntity.ok(service.register(request));
//    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody CreateUserRequest request) {
        log.info("into register of AuthenticationController");
        Response createdResponse = kcService.createKeycloakUser(request);
        return ResponseEntity.status(createdResponse.getStatus()).build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("into authenticate of AuthenticationController");
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping(value = "/realms/realm_01/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    public ResponseEntity<AccessTokenResponse> getAccessToken(@RequestParam Map<String,String> request) {
        log.info("into get access token of AuthenticationController");
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(request.get("username").toString(), request.get("password").toString()).build();
        //Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials("duydn1", "123123").build();

        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);
        } catch (BadRequestException ex) {
            log.warn("invalid ", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }
    }





}
