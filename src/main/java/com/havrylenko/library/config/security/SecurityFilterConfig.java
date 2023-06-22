package com.havrylenko.library.config.security;

import com.havrylenko.library.model.security.Role;
import com.havrylenko.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityFilterConfig {

    private final UserService userService;

    public SecurityFilterConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/register").anonymous()
                                .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.getAuthority())
                                .requestMatchers("/books/**", "/profile/**").hasAuthority(Role.READER.getAuthority())
                                .requestMatchers("/books/**", "/library/**").hasAuthority(Role.LIBRARIAN.getAuthority())
                                .anyRequest().authenticated())
                .formLogin((auth) ->
                        auth.loginPage("/login")
                                .successHandler(handler())
                                .permitAll())
                .logout((auth) ->
                        auth.logoutUrl("/logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessUrl("/login")
                                .permitAll())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth,
                                   BCryptPasswordEncoder encoder) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }

    private AuthenticationSuccessHandler handler() {
        return (req, resp, auth) -> {
            var role = auth.getAuthorities().iterator().next();
            if (role.getAuthority().equals(Role.ADMIN.getAuthority())){
                resp.sendRedirect("/admin");
            } else if (role.getAuthority().equals(Role.LIBRARIAN.getAuthority())) {
                resp.sendRedirect("/library");
            } else {
                userService.changeLoginDate(auth.getName());
                resp.sendRedirect("/books");
            }
        };
    }
}
