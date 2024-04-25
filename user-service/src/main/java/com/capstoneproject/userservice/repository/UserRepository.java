package com.capstoneproject.userservice.repository;

import com.capstoneproject.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUserName(String username);
}
