package coms309.login.bhall1.repository;

import coms309.login.bhall1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u where u.email = ?1")
    Optional<User> findByEmail(String email);
}