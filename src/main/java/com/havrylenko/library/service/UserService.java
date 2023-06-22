package com.havrylenko.library.service;

import com.havrylenko.library.model.security.Role;
import com.havrylenko.library.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("admin")) {
            return User.builder()
                    .username("admin")
                    .password("admin")
                    .authorities(Role.ADMIN.getAuthority())
                    .passwordEncoder(passwordEncoder::encode)
                    .build();
        }
        var user = userRepository.getUserByUsername(username);
        return user.orElse(null);
    }

    public void changeLoginDate(String username) {
        var user = userRepository.getUserByUsername(username).get();
        user.setLastLoginTime(LocalDateTime.now());
        userRepository.save(user);
    }
}
