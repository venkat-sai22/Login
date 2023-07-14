package com.example.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.login.entity.User;

//UserRepository.java

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {

 User findByUsername(String username);

 boolean existsByUsername(String username);
}
