package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.ReaderDTO;
import com.havrylenko.library.model.enums.Gender;
import com.havrylenko.library.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Period;

@Controller
@RequestMapping("library/reports")
public class LibrarianReportsController {

    private final ReaderService readerService;

    public LibrarianReportsController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    public String getView() {
        return "/librarian/librarian_reports";
    }

    @GetMapping("/readers")
    public String get(@RequestParam(required = false) String gender,
                      @RequestParam(required = false, defaultValue = "0") int ageFrom,
                      @RequestParam(required = false, defaultValue = "0") int ageTo,
                      Model model) {
        var readers = readerService.getAll();
        if (Gender.MALE.name().equals(gender)) {
            readers = readers.stream()
                    .filter(r -> Gender.MALE.equals(r.getPersonDetails().getGender()))
                    .toList();
        }
        if (Gender.FEMALE.name().equals(gender)) {
            readers = readers.stream()
                    .filter(r -> Gender.FEMALE.equals(r.getPersonDetails().getGender()))
                    .toList();
        }
        if (ageFrom != 0 && ageTo != 0) {
            readers = readers.stream()
                    .filter(r -> {
                        var dob =r.getPersonDetails().getDateOfBirth();
                        if (dob == null)
                            return false;
                        int fullYears = Period.between(dob,
                                LocalDate.now()).getYears();
                        return fullYears >= ageFrom && fullYears <= ageTo;
                    })
                    .toList();
        }
        model.addAttribute("readers", readers.stream().map(ReaderDTO::fromReader).toList());
        return "/librarian/librarian_readers";
    }

}
