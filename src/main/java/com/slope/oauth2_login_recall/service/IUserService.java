package com.slope.oauth2_login_recall.service;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.slope.oauth2_login_recall.data.LocalAuthUser;

public interface IUserService {

  LocalAuthUser registerOauth2User(OAuth2User auth2User);
}
