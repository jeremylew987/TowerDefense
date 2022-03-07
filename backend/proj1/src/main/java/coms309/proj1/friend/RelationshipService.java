package coms309.proj1.friend;

import coms309.proj1.user.User;
import coms309.proj1.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipService
{
	@Autowired
	RelationshipRepository relationshipRepository;

	@Autowired
	UserRepository userRepository;


	public void saveRelationship(User owner, User friend) {

	}

	public List<User> getRelationships(User user) {

	}





}
