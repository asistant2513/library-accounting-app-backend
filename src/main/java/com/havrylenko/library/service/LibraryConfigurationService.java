package com.havrylenko.library.service;

import com.havrylenko.library.model.config.LibraryConfiguration;
import com.havrylenko.library.repository.LibraryConfigurationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LibraryConfigurationService {

    private LibraryConfigurationRepository repository;

    public LibraryConfigurationService(final LibraryConfigurationRepository repository) {
        this.repository = repository;
    }

    public LibraryConfiguration getConfiguration() {
        return repository.findAll().get(0);
    }

    public LibraryConfiguration applyConfiguration(final LibraryConfiguration configuration) {
        return repository.save(configuration);
    }
}
