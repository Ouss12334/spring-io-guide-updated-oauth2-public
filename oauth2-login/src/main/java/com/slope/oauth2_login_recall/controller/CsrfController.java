package com.slope.oauth2_login_recall.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * for ajax/mobile usage
 */
@RestController
public class CsrfController {

    @GetMapping("/csrf")
    public CsrfToken getCsrf(CsrfToken csrfToken) {
        return csrfToken;
    }
    
}
