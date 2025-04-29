package com.slope.oauth2_login_recall.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * not found WebSecurityConfigurerAdapter but this shows it was deprecated https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 * replaced with bean creation
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((auth) -> 
            auth
            .requestMatchers("/static/access-**", "/", "/error", "/webjars/**", "/access**").permitAll()
            .anyRequest().authenticated()
        ).exceptionHandling((exceptionCustomizer) -> 
            exceptionCustomizer
            .accessDeniedPage("/access/denied")
        ).oauth2Login( 
            // withDefaults()
            oauth2Customizer -> oauth2Customizer
            .failureHandler((request, response, exception) -> {
                log.info("handling auth failure: {}", exception.getMessage());
                // redirects to 401 page 
                response.sendRedirect("/access-401.html?error=".concat(exception.getMessage()));
            })
        )
        .oauth2Client(withDefaults())
        .logout(logoutCustomizer -> logoutCustomizer
            .invalidateHttpSession(true)
            // .logoutUrl("/logout") // default is /logout
            .logoutSuccessUrl("/") // redirect to homepage after logout
            .deleteCookies("JSESSIONID", "XSRF-TOKEN")
            .permitAll())
        .csrf(
            withDefaults()
            // .disable() // works when disabled w/ get/post/etc..
        )
        .build();
    }

    /**
     * SAML
     */
    // @Bean
    // SecurityFilterChain samlSecurityFilterChain(HttpSecurity http) throws Exception {
    //     http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
    //     http.saml2Login(withDefaults());
    //     http.saml2Logout(withDefaults());
    //     return http.build();
    // }

}
