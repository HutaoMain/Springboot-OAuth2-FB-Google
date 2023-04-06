package com.oauth.oauth.controller;

import com.oauth.oauth.model.User;
import com.oauth.oauth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class SocialController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public Map<String, Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken) {

        Map<String, Object> attributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        String provider = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(); // get the provider name from the token

        String pictureUrl = "google";

        if (provider.equals("facebook")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> picture = (Map<String, Object>) attributes.get("picture");
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) picture.get("data");
            pictureUrl = (String) data.get("url");
        } else  {
            pictureUrl = (String) attributes.get("picture");
        }

        boolean emailExists = userRepository.existsByEmail(email);
        if (emailExists) {
            log.info("User with email {} already exists in the database, skipping save", email);
        } else {

            User user = new User();
            user.setEmail(email);
            user.setUsername(email);
            user.setName(name);
            user.setImageUrl(pictureUrl);
            userRepository.save(user);
            log.info("User saved to database with email {}", email);
        }
        return attributes;
    }
}
