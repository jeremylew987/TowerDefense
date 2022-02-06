package coms309.trivia.bhall1.repository;

import coms309.trivia.bhall1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}