package com.study.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.back.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    // SELECT * FROM user WHERE username = ?
    User findByUsername(String username);

    User findByUserEmail(String userEmail);

    // SELECT * FROM user WHERE provider = ? and providerId = ?
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
