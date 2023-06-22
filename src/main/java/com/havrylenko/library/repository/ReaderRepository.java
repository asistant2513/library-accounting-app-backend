package com.havrylenko.library.repository;

import com.havrylenko.library.model.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, String> {
    Optional<Reader> findByUsername(String username);
}
