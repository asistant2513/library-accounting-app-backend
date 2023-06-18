package com.havrylenko.library.repository;

import com.havrylenko.library.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> getUserByUsername(final String username);
}
