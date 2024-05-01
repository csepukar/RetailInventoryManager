package com.capstoneproject.userservice.repository;

//import com.capstoneproject.userservice.helper.RefreshableCRUDRepository;
import com.capstoneproject.userservice.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);
}