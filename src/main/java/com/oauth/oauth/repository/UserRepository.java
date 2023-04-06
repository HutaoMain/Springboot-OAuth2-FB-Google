package com.oauth.oauth.repository;

import com.oauth.oauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*
    * we use Optional to handle the case where the user may not exist in the database.
    * If the user is not found, the findById method returns an empty Optional object,
    * which allows us to avoid NullPointerException by calling orElse method on it.
    */
    Optional<User> findByEmail(String email);
}
