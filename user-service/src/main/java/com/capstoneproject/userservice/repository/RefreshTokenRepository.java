package com.capstoneproject.userservice.repository;

//import com.capstoneproject.userservice.helper.RefreshableCRUDRepository;
import com.capstoneproject.userservice.model.RefreshToken;
import com.capstoneproject.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);
    void deleteAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.userInfo.id = :userId")
    void deleteAllByUserId(@Param("userId") long userId);
}