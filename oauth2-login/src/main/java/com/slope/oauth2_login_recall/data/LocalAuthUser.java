package com.slope.oauth2_login_recall.data;

// record fields are final
public record LocalAuthUser(
  String oauthId
  ,String fullname
  ,String grantedAuthority
  ,String profileUrl
  ,String picUrl
  ,boolean usesGoogleAuth
  ,boolean usesGithubAuth) {

  // add default ctor in record
  public LocalAuthUser() {
    this(null, null, null, null, null, false, false);
  }

}
