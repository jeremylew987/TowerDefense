package coms309.proj1.friend;

import coms309.proj1.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer>
{
	/*
	 * Return true if a User (owner) is friends with another User (Friend)
	 */
	boolean existsByOwnerAndFriend(User owner, User friend);
	List<Friendship> findByOwner(User user);
	List<Friendship> findByFriend(User user);
	Optional<Friendship> findFirstByOwnerAndFriend(User owner, User friend);

}
