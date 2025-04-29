package com.slope.oauth2_login_recall.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestClient;

import lombok.extern.slf4j.Slf4j;

/**
 * custom validation before accepting login with github 
 * requires user to be part of "x" github organisation (spring-boot for eg)
 * can be applied to any client (google,etc.) 
 */
@Slf4j
@Configuration
public class GithubValidatorConfiguration {

  @Bean
  public RestClient restClient(RestClient.Builder builder) {
    return builder
      .build();
  }

  @Bean
  OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(RestClient restClient) {
    DefaultOAuth2UserService service = new DefaultOAuth2UserService();
    return oauth2UserRequest -> {
      OAuth2User user = service.loadUser(oauth2UserRequest);
      // ignore if not github
      if (!"github".equals(oauth2UserRequest.getClientRegistration().getRegistrationId())) return user;

      var authorizedClient = new OAuth2AuthorizedClient(oauth2UserRequest.getClientRegistration(), user.getName(),oauth2UserRequest.getAccessToken());

      var githubRepos = restClient.get()
      // .uri(user.getAttributes().get("organizations_url").toString())
      .uri(user.getAttributes().get("repos_url").toString()) // use repos instead of orgs because is empty
      // equivalent to attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(authorizedClient));
      .attributes((attributes) -> attributes.put(OAuth2AuthorizedClient.class.getName(), authorizedClient))
      .retrieve()
      .toEntity(new ParameterizedTypeReference<List<Map<String, Object>>>(){});

      if (githubRepos.getBody()
          .stream()
          .anyMatch(repo -> "starsector-tuto-mod".equals(repo.get("name")))
        ) 
          return user;
          
      log.info("invalid starsector repo");
      throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN, "not a part of the starsector team", ""));
    };
  }
}
