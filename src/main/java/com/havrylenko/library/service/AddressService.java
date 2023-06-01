package com.havrylenko.library.service;

import com.havrylenko.library.model.entity.Address;
import com.havrylenko.library.repository.AddressRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class AddressService {
    private final AddressRepository repository;

    public AddressService(final AddressRepository repository) {
        this.repository = repository;
    }

    public List<Address> getAll() {
        return repository.findAll();
    }

    public Optional<Address> getOneById(final Long id) {
        return repository.findById(id);
    }

    public Address save(final Address address) {
        return repository.save(address);
    }

    public void delete(final Address address) {
        repository.delete(address);
    }

    public void deleteById(final Long id) {
        repository.deleteById(id);
    }
}
