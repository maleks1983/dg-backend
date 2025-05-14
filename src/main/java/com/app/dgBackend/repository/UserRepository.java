package com.app.dgBackend.repository;

import com.app.dgBackend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByTel(String tel); // тобто логін за телефоном
}