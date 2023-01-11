package com.demo.authen.services;

import com.demo.authen.model.User;
import com.demo.authen.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    //role user can use it
    public ResponseEntity<User> findUserById(Long id) {
        User user = userRepo.findById(id).orElse(null);
        return ResponseEntity.ok(user);
    }

    //role admin can use it
    public List<User> getAllUser(){
        List<User> listUsers = userRepo.findAll();
        return listUsers;
    }


}
