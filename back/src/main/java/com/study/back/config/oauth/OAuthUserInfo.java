package com.study.back.config.oauth;

public interface OAuthUserInfo {
    String getProviderId();

    String getProvider();

    String getEmail();

    String getName();
}
