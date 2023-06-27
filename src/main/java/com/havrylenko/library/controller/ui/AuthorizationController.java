package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.LibrarianSelectDTO;
import com.havrylenko.library.model.dto.LoginDTO;
import com.havrylenko.library.model.dto.RegistrationDTO;
import com.havrylenko.library.model.entity.Address;
import com.havrylenko.library.model.entity.PersonDetails;
import com.havrylenko.library.model.entity.Reader;
import com.havrylenko.library.model.entity.ReaderCard;
import com.havrylenko.library.model.enums.Gender;
import com.havrylenko.library.model.security.Role;
import com.havrylenko.library.repository.UserRepository;
import com.havrylenko.library.service.*;
import com.havrylenko.library.util.JsonConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Controller
public class AuthorizationController {

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final LibrarianService librarianService;
    private final ReaderService readerService;
    private final PersonDetailsService personDetailsService;
    private final ReaderCardService readerCardService;
    private final AddressService addressService;

    public AuthorizationController(UserRepository repository,
                                   BCryptPasswordEncoder encoder,
                                   LibrarianService librarianService,
                                   ReaderService readerService,
                                   PersonDetailsService personDetailsService,
                                   ReaderCardService readerCardService,
                                   AddressService addressService) {
        this.repository = repository;
        this.encoder = encoder;
        this.librarianService = librarianService;
        this.readerService = readerService;
        this.personDetailsService = personDetailsService;
        this.readerCardService = readerCardService;
        this.addressService = addressService;
    }

    @GetMapping("login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginForm", new LoginDTO());
        return "login.html";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication) {
        authentication.setAuthenticated(false);
        return "redirect:/login";
    }

    @GetMapping("register")
    public String getRegistrationPage(Model model) {
        var librarians = librarianService.getAll().stream()
                .map( l -> new LibrarianSelectDTO(l.getUserId(),
                        String.format("%s %s %s",
                                l.getPersonDetails().getName(),
                                l.getPersonDetails().getSurname(),
                                l.getPersonDetails().getPaternity())))
                .collect(Collectors.toList());
        model.addAttribute("librarians", librarians);
        model.addAttribute("regForm", RegistrationDTO.getInstance());
        return "registration.html";
    }

    @PostMapping("register")
    public String register(@ModelAttribute("regForm") RegistrationDTO dto) {
        //TODO: check passwords match
        Reader reader = new Reader();
        reader.setUsername(dto.username());
        reader.setPassword(encoder.encode(dto.password()));
        var librarian = librarianService.getOneById(dto.librarianId());

        ReaderCard card = new ReaderCard();
        card.setIssuedOn(LocalDate.now());
        card.setExpiresOn(LocalDate.now().plus(60, ChronoUnit.DAYS));
        card.setIssuedBy(librarian.get());

        readerCardService.save(card);

        Address address = JsonConverter.convertJsonToAddress(dto.address());
        address = addressService.save(address);

        PersonDetails details = PersonDetails.builder()
                .name(dto.name())
                .surname(dto.surname())
                .paternity(dto.paternity())
                .gender(Gender.valueOf(dto.gender()))
                .dateOfBirth(dto.dateOfBirth())
                .mobilePhone(dto.mobilePhone())
                .address(address)
                .build();

        personDetailsService.save(details);

        reader.setReaderCard(card);
        reader.setPersonDetails(details);
        reader.setUserRole(Role.READER);
        reader.setLocked(true);

        readerService.save(reader);
        return "redirect:login";
    }
}
