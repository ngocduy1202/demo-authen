package com.demo.authen.services;

import com.demo.authen.auth.AuthenticationRequest;
import com.demo.authen.auth.AuthenticationResponse;
import com.demo.authen.constant.Constants;
import com.demo.authen.dto.CreateUserRequest;
import com.demo.authen.demoenum.Role;
import com.demo.authen.dto.RegisterResponse;
import com.demo.authen.model.User;
import com.demo.authen.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(CreateUserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .timeCreated(new Date())
                .timeUpdated(new Date())
                .build();
        userRepo.save(user);

        return RegisterResponse.builder()
                .username(user.getUsername())
                .message("Register successful.")
                .timeCreated(new Date())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //authen verify token

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        com.demo.authen.model.User user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .tokenType(Constants.TOKEN_TYPE)
                .build();
    }
}
