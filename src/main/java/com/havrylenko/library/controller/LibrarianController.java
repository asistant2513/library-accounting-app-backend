package com.havrylenko.library.controller;

import com.havrylenko.library.model.dto.AddressDTO;
import com.havrylenko.library.model.dto.LibrarianDTO;
import com.havrylenko.library.model.dto.NewUserDTO;
import com.havrylenko.library.model.entity.Address;
import com.havrylenko.library.model.entity.Librarian;
import com.havrylenko.library.model.entity.PersonDetails;
import com.havrylenko.library.model.security.Role;
import com.havrylenko.library.service.AddressService;
import com.havrylenko.library.service.LibrarianService;
import com.havrylenko.library.service.PersonDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("v1/librarians")
public class LibrarianController {
    private final LibrarianService service;
    private final PersonDetailsService personDetailsService;
    private final AddressService addressService;
    private final BCryptPasswordEncoder encoder;

    public LibrarianController(final LibrarianService service,
                               final PersonDetailsService personDetailsService,
                               final AddressService addressService,
                               final BCryptPasswordEncoder encoder) {
        this.service = service;
        this.personDetailsService = personDetailsService;
        this.addressService = addressService;
        this.encoder = encoder;
    }

    @GetMapping
    public ResponseEntity<List<LibrarianDTO>> getAllLibrarians() {
        return ResponseEntity.ok(service.getAll().stream().map(LibrarianDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibrarianDTO> getLibrarianByID(@PathVariable final String id) {
        Optional<Librarian> optionalLibrarian = service.getOneById(id);
        LibrarianDTO dto;
        if (optionalLibrarian.isPresent()) {
            dto = new LibrarianDTO(optionalLibrarian.get());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<LibrarianDTO> addLibrarian(@RequestBody final NewUserDTO dto) {
        String hash = encoder.encode(dto.getPassword());

        PersonDetails personDetails = new PersonDetails();
        Address address = new Address();
        address = addressService.save(address);
        personDetails.setAddress(address);
        personDetails = personDetailsService.save(personDetails);

        Librarian librarian = new Librarian(dto.getUsername(), hash, personDetails);
        librarian.setUserRole(Role.LIBRARIAN);
        return new ResponseEntity<>(new LibrarianDTO(service.save(librarian)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LibrarianDTO> modifyLibrarian(@PathVariable final String id,
                                                        @RequestBody final LibrarianDTO dto) {
        Optional<Librarian> optionalLibrarian = service.getOneById(id);
        if (optionalLibrarian.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        final Librarian librarian = optionalLibrarian.get();
        librarian.setUsername(dto.getUserDetails().getUsername());
        librarian.setLocked(dto.getUserDetails().isLocked());

        if(!Objects.isNull(dto.getPersonDetails())) {
            final PersonDetails details = librarian.getPersonDetails();
            details.setName(dto.getPersonDetails().getName());
            details.setSurname(dto.getPersonDetails().getSurname());
            details.setPaternity(dto.getPersonDetails().getPaternity());
            details.setGender(dto.getPersonDetails().getGender());
            details.setDateOfBirth(dto.getPersonDetails().getDateOfBirth());
            details.setMobilePhone(dto.getPersonDetails().getMobilePhone());

            if(!Objects.isNull(dto.getPersonDetails().getAddress())) {
                final Address address = details.getAddress();
                final AddressDTO addressDto = dto.getPersonDetails().getAddress();
                address.setCountry(addressDto.getCountry());
                address.setCity(addressDto.getCity());
                address.setDistrict(addressDto.getDistrict());
                address.setVillage(addressDto.getVillage());
                address.setStreet(addressDto.getStreet());
                address.setBuilding(addressDto.getBuilding());
                address.setApartment(addressDto.getApartment());
                address.setZipCode(addressDto.getZipCode());
            }
        }
        return new ResponseEntity<>(new LibrarianDTO(service.save(librarian)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibrarian(@PathVariable final String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
