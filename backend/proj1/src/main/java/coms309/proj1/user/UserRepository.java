package coms309.proj1.user;

import coms309.proj1.friend.FriendRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository // Indicates this class interacts with the database
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    //Optional<User> findAll(String userId);
    Optional<User> findByUserId(Long userId);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> deleteByUserId(Long userId);

    /*
     * Use returned entity for future operations as it could have been changed completely by save
     * Will never return null.
     */
    User save(User user);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = TRUE WHERE u.email = ?1")
    int enableUser(String email);


}