package com.oauth.oauth.api.facebook;

import com.oauth.oauth.api.ApiBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Facebook extends ApiBinding {

    private static final String GRAPH_API_BASE_URL =
            "https://graph.facebook.com/v3.2";

    public Facebook(String accessToken) {
        super(accessToken);
    }

//    public Profile getProfile() {
//        return restTemplate.getForObject(
//                GRAPH_API_BASE_URL + "/me", Profile.class);
//    }

    public Profile getProfile() {
        Profile profile = restTemplate.getForObject(GRAPH_API_BASE_URL + "/me?fields=id,email,name,picture.type(large)", Profile.class);
        Logger logger = LoggerFactory.getLogger(Facebook.class);
        logger.info("Facebook API response: {}", profile);
        return profile;
    }
}
