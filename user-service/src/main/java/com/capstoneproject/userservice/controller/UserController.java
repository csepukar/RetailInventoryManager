package com.capstoneproject.userservice.controller;

import com.capstoneproject.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) throws Exception {

        System.out.println(jwtRequest);
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Bad Cred!!");
        }

        //fine code

        UserDetails user = this.userService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(user);
        System.out.println("Token = "+token);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody User user){

        return ResponseEntity.ok(userService.saveUser(user));
    }


    //Only ADMIN role can access this
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String test(){
        return "Welcome with token !!";
    }


    //Only USER role can access this
    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/welcomeuser", method = RequestMethod.GET)
    public String testuser(){
        return "Welcome with token USER !!";
    }
}