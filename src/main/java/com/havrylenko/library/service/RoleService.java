package com.havrylenko.library.service;

import com.havrylenko.library.model.security.Role;
import com.havrylenko.library.repository.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class RoleService {
    private final RoleRepository repository;

    public RoleService(final RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> getAll() {
        return repository.findAll();
    }

    public Optional<Role> getOneById(final Integer id) {
        return repository.findById(id);
    }

    public Role save(final Role role) {
        return repository.save(role);
    }

    public void delete(final Role role) {
        repository.delete(role);
    }

    public void deleteById(final Integer id) {
        repository.deleteById(id);
    }

    //TODO: add filters for reader reader
}
