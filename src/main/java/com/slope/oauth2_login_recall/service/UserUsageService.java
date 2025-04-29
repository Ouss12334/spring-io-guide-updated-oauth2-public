package com.slope.oauth2_login_recall.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.slope.oauth2_login_recall.data.LocalAuthUser;

import lombok.extern.slf4j.Slf4j;

/**
 * mock user registration like real services 
 */
@Slf4j
@Service
public class UserUsageService implements IUserService {

  // use the oauth/oidc id from the apis as key (is different even for the same person)
  private Map<String, LocalAuthUser> users = new HashMap<>();

  @Override
  public LocalAuthUser registerOauth2User(OAuth2User auth2User) {
    // check which oauth2 client
    var isGithubAuth = auth2User.getAttribute("repos_url") != null;
    
    // add new user if not exists
    if (!users.containsKey(auth2User.getName())) {
      var grantedAuthority = auth2User.getAuthorities().stream().findFirst().get().toString();
      
      var newUser = new LocalAuthUser();
      // if comes from github
      if (isGithubAuth) {
        var fullname = auth2User.getAttribute("login").toString();
        var profile = auth2User.getAttribute("url").toString();
        var picUrl = auth2User.getAttribute("avatar_url").toString();
        
        newUser = new LocalAuthUser(auth2User.getName(), fullname, grantedAuthority, profile, picUrl, false, true);
      } else {
        var fullname = auth2User.getAttribute("name").toString();
        var profile = auth2User.getAttribute("email").toString();
        var picUrl = auth2User.getAttribute("picture").toString();
        
        newUser = new LocalAuthUser(auth2User.getName(), fullname, grantedAuthority, profile, picUrl, true, false);
      }
      users.put(auth2User.getName(), newUser);
      return newUser;
    } else {
      return users.get(auth2User.getName());
    }
  }

}
