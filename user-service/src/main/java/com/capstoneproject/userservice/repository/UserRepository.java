package com.capstoneproject.userservice.repository;

import com.capstoneproject.userservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
    // User findFirstById(Long id);
}