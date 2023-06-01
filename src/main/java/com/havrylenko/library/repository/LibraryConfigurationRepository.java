package com.havrylenko.library.repository;

import com.havrylenko.library.model.config.LibraryConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryConfigurationRepository extends JpaRepository<LibraryConfiguration, Short> {
}
