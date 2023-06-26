package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.BookDTO;
import com.havrylenko.library.model.dto.ReaderDTO;
import com.havrylenko.library.model.entity.Address;
import com.havrylenko.library.model.entity.PersonDetails;
import com.havrylenko.library.model.enums.Gender;
import com.havrylenko.library.service.AddressService;
import com.havrylenko.library.service.BookService;
import com.havrylenko.library.service.PersonDetailsService;
import com.havrylenko.library.service.ReaderService;
import com.havrylenko.library.util.JsonConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ReaderProfileController {

    private final BookService bookService;
    private final ReaderService readerService;
    private final AddressService addressService;

    private final PersonDetailsService personDetailsService;

    public ReaderProfileController(BookService bookService,
                                   ReaderService readerService,
                                   AddressService addressService,
                                   PersonDetailsService personDetailsService) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.addressService = addressService;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping
    public String get(Model model,
                      Principal principal) {
        var reader = ReaderDTO.fromReader(readerService.getOneByUsername(principal.getName()).get(), false);
        var books = bookService.getBooksByReaderId(reader.id()).stream().map(BookDTO::fromBook).toList();

        model.addAttribute("reader", reader);
        model.addAttribute("books", books);

        return "/reader/reader_profile";
    }

    @GetMapping("/edit")
    public String getEdit(Model model,
                      Principal principal) {
        var reader = ReaderDTO.fromReader(readerService.getOneByUsername(principal.getName()).get(), true);
        model.addAttribute("reader", reader);
        return "/reader/edit_reader_profile";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("reader") ReaderDTO dto,
                          Principal principal) {
        var reader = readerService.getOneByUsername(principal.getName()).get();

        Address address;
        if(reader.getPersonDetails().getAddress() == null) {
            address = new Address();
        } else {
            address = reader.getPersonDetails().getAddress();
        }
        Address newAddress = JsonConverter.convertJsonToAddress(dto.address());
        address.updateFieldsFrom(newAddress);

        address = addressService.save(address);

        PersonDetails details = reader.getPersonDetails();
        details.setName(dto.name());
        details.setSurname(dto.surname());
        details.setPaternity(dto.paternity());
        details.setGender(Gender.valueOf(dto.gender()));
        details.setDateOfBirth(dto.dateOfBirth());
        details.setMobilePhone(dto.mobilePhone());
        details.setAddress(address);

        personDetailsService.save(details);
        return "redirect:/profile";
    }
}
