package coms309.proj1.friend;

import coms309.proj1.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Integer>
{
	boolean existsByOwnerAndFriend(User owner, User friend);
	List<FriendRelationship> findByOwner(User user);
	List<FriendRelationship> findByFriend(User user);

}
