package com.capstoneproject.userservice.repository;

import com.spring3.oauth.jwt.helpers.RefreshableCRUDRepository;
import com.capstoneproject.userservice.model.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends RefreshableCRUDRepository<User, Long> {
    public User findByUsername(String username);
    User findFirstById(Long id);
}