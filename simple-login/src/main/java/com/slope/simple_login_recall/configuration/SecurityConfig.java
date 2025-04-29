package com.slope.simple_login_recall.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests((authorizeHttpRequestsCustomizer) -> authorizeHttpRequestsCustomizer
            .requestMatchers("/", "/home").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin((formCustomizer) -> formCustomizer.loginPage("/login").permitAll())
        .logout((logoutCustomizer) -> logoutCustomizer.permitAll());
        
        return security.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        var user = User.builder()
        .passwordEncoder(psw -> PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(psw))
        .username("user")
        .password("user")
        .roles("USER")
        .build();

        return new InMemoryUserDetailsManager(user);
    }
}
