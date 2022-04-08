package coms309.proj1.friend;

import coms309.proj1.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer>
{
	/*
	 * Return true if a User (owner) is friends with another User (Friend)
	 */
	boolean existsBySenderAndReceiver(User sender, User receiver);
	List<FriendRequest> findBySender(User user);
	List<FriendRequest> findByReceiver(User user);
	Optional<FriendRequest> findFirstBySenderAndReceiver(User sender, User receiver);

}
