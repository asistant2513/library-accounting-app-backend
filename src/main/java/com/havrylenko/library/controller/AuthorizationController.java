package com.havrylenko.library.controller;

import com.havrylenko.library.model.dto.LoginDTO;
import com.havrylenko.library.model.security.User;
import com.havrylenko.library.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthorizationController {

    private final UserRepository repository;

    private final BCryptPasswordEncoder encoder;

    public AuthorizationController(UserRepository repository,
                                   BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @GetMapping("login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginForm", new LoginDTO());
        return "login.html";
    }

    @PostMapping("login")
    public String performLogin(@ModelAttribute("loginForm") LoginDTO dto) {
        Optional<User> userOptional = repository.getUserByUsername(dto.getUsername());
        if(userOptional.isPresent()) {
            if(!encoder.matches(dto.getPassword(), userOptional.get().getPassword())) {
                return "login.html";
            }
        }
        return "/";
    }
}
