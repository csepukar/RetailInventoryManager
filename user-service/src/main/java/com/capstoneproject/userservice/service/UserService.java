package com.capstoneproject.userservice.service;

import java.util.List;


public interface UserService {
    UserResponse saveUser(UserRequest userRequest);
    UserResponse getUser();
    List<UserResponse> getAllUser();
}