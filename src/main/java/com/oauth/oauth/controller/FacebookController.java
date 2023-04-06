package com.oauth.oauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Slf4j
@RestController
@CrossOrigin("*")
public class FacebookController {

    @Value("${spring.security.oauth2.client.registration.facebook.redirectUri}")
    private String redirectUri; // redirectCallback

    @Value("${spring.security.oauth2.client.provider.facebook.authorizationUri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.registration.facebook.scope}")
    private String scope;

    @Value("${spring.security.oauth2.client.registration.facebook.clientId}")
    private String clientId;

    @Value("${client.url}")
    private String clientUrl;


    @GetMapping("/auth/facebook")
    public void startFacebookAuth(HttpServletRequest request, HttpServletResponse response) {
        try {
            String facebookAuthorizeUrl = authorizationUri + "?response_type=code&redirect_uri=" + redirectUri + "&scope=" + scope + "&client_id=" + clientId;
            log.info("Facebook OAuth Login Initiated");
            facebookAuthorizeUrl = facebookAuthorizeUrl
                    .replace("{redirect_uri}", redirectUri)
                    .replace("{scope}", scope)
                    .replace("{client_id}", clientId);
            response.sendRedirect(facebookAuthorizeUrl);
        } catch (IOException e) {
            log.error("Error while initiating Facebook OAuth Login: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @GetMapping("/oauth2/callback/facebook")
    public Map<String, Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        log.info("principal: {}", oAuth2AuthenticationToken.getPrincipal().getAttributes());
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }
}
