package com.example.w2m.repository;

import com.example.w2m.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);


}
