package com.capstoneproject.userservice.service;

import com.capstoneproject.userservice.model.RefreshToken;
import com.capstoneproject.userservice.model.User;
import com.capstoneproject.userservice.repository.RefreshTokenRepository;
import com.capstoneproject.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public RefreshToken createRefreshToken(String username){

        // Delete previous token if any
        User user = userRepository.findByUsername(username);

        deleteByUserId(user.getId());
        // Continue rest of the process
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userRepository.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

    public void deleteByUserId(long userId) {
        User user = userRepository.findFirstById(userId);
        if (user != null) {
            refreshTokenRepository.deleteAllByUserId(user.getId());
        } else {
            // Handle the case where the user with the given ID does not exist
        }
    }

    public void deleteAll() {
        refreshTokenRepository.deleteAll();
    }

}
