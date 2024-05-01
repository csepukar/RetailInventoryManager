package com.capstoneproject.userservice.controller;

import com.capstoneproject.userservice.dto.*;
//import com.capstoneproject.userservice.model.RefreshToken;
import com.capstoneproject.userservice.model.RefreshToken;
import com.capstoneproject.userservice.service.InMemoryTokenBlacklist;
import com.capstoneproject.userservice.service.JwtService;
//import com.capstoneproject.userservice.service.RefreshTokenService;
import com.capstoneproject.userservice.service.RefreshTokenService;
import com.capstoneproject.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;
    private final InMemoryTokenBlacklist tokenBlacklist = new InMemoryTokenBlacklist();

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        // Check if the user is already authenticated
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            // User is already authenticated, return an HTTP response indicating this
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User is already authenticated.");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            return ResponseEntity.ok(JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .token(refreshToken.getToken())
                    .build());

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Delete unreachable statement");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);

        // Find the user associated with the access token
        String username = jwtService.extractUsername(token);

        if (userService.existsByUsername(username)) {
            refreshTokenService.deleteRefreshToken(username);
        } else {
            ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User refresh token doesn't exist");
        }

        // this is in memory list, try to implement redis cache if time
        tokenBlacklist.addToBlacklist(token);

        //TODO: Clear any session-related data if necessary
        return ResponseEntity.ok("Logged out successfully");
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        // Get the Authorization header from the request
        String authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is not null and starts with "Bearer "
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            // Extract the JWT token (remove "Bearer " prefix)
            return authorizationHeader.substring(7);
        }

        // If the Authorization header is not valid, return null
        return null;
    }

    @PostMapping(value = "/save")
    public ResponseEntity saveUser(@RequestBody UserRequest userRequest) {
        try {
            // Check if the user exists by username
            if (userService.existsByUsername(userRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("User with username '" + userRequest.getUsername() + "' already exists.");
            }

            // Save the user
            UserResponse userResponse = userService.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        try {
            List<UserResponse> userResponses = userService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        try {
            UserResponse userResponse = userService.getUser();
            return ResponseEntity.ok().body(userResponse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
