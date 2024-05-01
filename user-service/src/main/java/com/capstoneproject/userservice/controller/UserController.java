package com.capstoneproject.userservice.controller;

import com.capstoneproject.userservice.dto.*;
//import com.capstoneproject.userservice.model.RefreshToken;
//import com.capstoneproject.userservice.service.RefreshTokenService;
import com.capstoneproject.userservice.service.RefreshTokenService;
import com.capstoneproject.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserResponse> userResponses = userService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        try {
            UserResponse userResponse = userService.getUser();
            return ResponseEntity.ok().body(userResponse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
