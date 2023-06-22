package com.havrylenko.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
public class TemplateConfig {
    @Bean
    public IDialect getJava8Dialect() {
        return new Java8TimeDialect();
    }

    @Bean
    public IDialect getSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
