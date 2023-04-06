package com.oauth.oauth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth.oauth.api.facebook.Facebook;
import com.oauth.oauth.api.facebook.Profile;
import com.oauth.oauth.model.User;
import com.oauth.oauth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin("*")
public class FacebookController {

    @Autowired
    UserRepository userRepository;

    private final Facebook facebook;

    @Autowired
    public FacebookController(Facebook facebook) {
        this.facebook = facebook;
    }

    @GetMapping("/user")
    public String getProfileAndSave(Model model) throws JsonProcessingException {
        Profile profile = facebook.getProfile();
        ObjectMapper objectMapper = new ObjectMapper();
        String profileJson = objectMapper.writeValueAsString(profile);
        log.info("Facebook Profile JSON: {}", profileJson);

        String email = profile.getEmail();
        boolean emailExists = userRepository.existsByEmail(email);

        if (emailExists) {
            log.info("User with email {} already exists in the database, skipping save", email);
        } else {
            User user = new User();
            user.setEmail(email);
            user.setUsername(email);
            user.setName(profile.getName());
            user.setImageUrl(profile.getPicture().getData().getUrl());
            userRepository.save(user);
            log.info("User saved to database with email {}", email);
        }

        model.addAttribute("profile", profile);
        model.addAttribute("email", profile.getEmail());
        model.addAttribute("pictureUrl", profile.getPicture().getData().getUrl());
        log.info("model {}", model);
        return "home";
    }
}
