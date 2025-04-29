package com.slope.oauth2_login_recall.controller;

import java.util.Map;

// import org.springframework.core.io.ClassPathResource;
// import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/access")
public class AccessController {

    // test by commenting _csrf in ajax $.post() index.html
    // @CrossOrigin
    // @GetMapping("/access/denied")
    @PostMapping("/denied")
    public /*ResponseEntity<Resource>*/ ResponseEntity<Map<String, String>> accessDenied() {
        log.info("access denied");
        return ResponseEntity
        .badRequest()
        .body(Map.of("access", "denied"));
        // response.sendRedirect("/error");
        // return "redirect:/access-denied";
        // return ResponseEntity
        // .ok()
        // .body(new ClassPathResource("static/access-denied.html"));
    }
}
