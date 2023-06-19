package com.havrylenko.library.controller.rest;

import com.havrylenko.library.model.dto.restDto.AddressDTO;
import com.havrylenko.library.model.dto.restDto.NewUserDTO;
import com.havrylenko.library.model.dto.restDto.ReaderDTO;
import com.havrylenko.library.model.entity.*;
import com.havrylenko.library.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("v1/readers")
public class ReaderController {
    
    private final ReaderService service;
    private final PersonDetailsService personDetailsService;
    private final AddressService addressService;
    private final ReaderCardService readerCardService;
    private final LibrarianService librarianService;
    private final BCryptPasswordEncoder encoder;
    
    public ReaderController(final ReaderService service,
                            final PersonDetailsService personDetailsService,
                            final AddressService addressService,
                            final ReaderCardService readerCardService,
                            final LibrarianService librarianService,
                            final BCryptPasswordEncoder encoder) {
        this.service = service;
        this.personDetailsService = personDetailsService;
        this.addressService = addressService;
        this.readerCardService = readerCardService;
        this.librarianService = librarianService;
        this.encoder = encoder;
    }

    @GetMapping
    public ResponseEntity<List<ReaderDTO>> getAllReaders() {
        return ResponseEntity.ok(service.getAll().stream().map(ReaderDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderDTO> getReaderByID(@PathVariable final String id) {
        Optional<Reader> optionalReader = service.getOneById(id);
        if (optionalReader.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ReaderDTO(optionalReader.get()));
    }

    @PostMapping
    public ResponseEntity<ReaderDTO> addReader(@RequestBody final NewUserDTO dto,
                                            @RequestParam final String issuerId) {
        String hash = encoder.encode(dto.getPassword());

        PersonDetails personDetails = new PersonDetails();
        Address address = new Address();
        address = addressService.save(address);
        personDetails.setAddress(address);
        personDetails = personDetailsService.save(personDetails);

        Optional<Librarian> optionalLibrarian = librarianService.getOneById(issuerId);
        if (optionalLibrarian.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        ReaderCard readerCard = new ReaderCard();
        readerCard.setIssuedOn(LocalDate.now());
        readerCard.setExpiresOn(LocalDate.now().plusYears(1));
        readerCard.setIssuedBy(optionalLibrarian.get());
        readerCardService.save(readerCard);

        Reader reader = new Reader(dto.getUsername(), hash, personDetails, readerCard);
        return new ResponseEntity<>(new ReaderDTO(service.save(reader)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReaderDTO> modifyReader(@PathVariable final String id,
                                               @RequestBody final ReaderDTO dto) {

        Optional<Reader> optionalReader = service.getOneById(id);
        if (optionalReader.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        final Reader reader = optionalReader.get();
        reader.setUsername(dto.getUserDetails().getUsername());
        reader.setLocked(dto.getUserDetails().isLocked());

        if(!Objects.isNull(dto.getPersonDetails())) {
            final PersonDetails details = reader.getPersonDetails();
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
        return ResponseEntity.ok(new ReaderDTO(service.save(reader)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable final String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
