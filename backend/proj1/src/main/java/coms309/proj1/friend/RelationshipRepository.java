package coms309.proj1.friend;

import coms309.proj1.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Integer>
{
	boolean existsByOwnerAndFriend(User owner, User friend);
	List<Relationship> findByOwner(User user);
	List<Relationship> findByFriend(User user);

}
