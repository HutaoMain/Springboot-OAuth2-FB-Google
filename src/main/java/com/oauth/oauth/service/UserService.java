package com.oauth.oauth.service;

import com.oauth.oauth.model.User;
import com.oauth.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

//    public User userDoesNotExist(String email) {
//        return userRepository.findByEmail(email).orElse(null);
//    }
//
////    public User createUser(String email) {
////        User user = new User();
////        user.setEmail(email);
////        user.setUsername(email);
////        user.setFirstName();
////        return userRepository.save(user);
////    }
}
