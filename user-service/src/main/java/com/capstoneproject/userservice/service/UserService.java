package com.capstoneproject.userservice.service;
import com.capstoneproject.userservice.dto.UserRequest;
import com.capstoneproject.userservice.dto.UserResponse;
import java.util.List;


public interface UserService {
    UserResponse saveUser(UserRequest userRequest);
    UserResponse getUser();
    List<UserResponse> getAllUser();
}