package com.slope.oauth2_login_recall.controller;

import org.springframework.web.bind.annotation.RestController;

import com.slope.oauth2_login_recall.service.IUserService;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user")
    public Map<String, String> getUser(@AuthenticationPrincipal OAuth2User auth2User) {
        if (auth2User == null) { log.info("user not logged in"); return Collections.emptyMap(); }
        log.info("registering new user: {}", userService.registerOauth2User(auth2User));
        return Collections.singletonMap("name", auth2User.getAttribute("name"));
    }
    
}
