package com.jminardi.exp2.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>
{
	List<Users> findAll();
	Collection<Users> findById(@Param("id") int id);
	void deleteById(@Param("id") int id);
	Optional<Users> findByUsername(@Param("username") String username);
	Optional<Users> findByPassword(@Param("password") String password);
	Optional<Users> findByFirstName(@Param("firstName") String firstName);
	Optional<Users> findByLastName(@Param("lastName") String lastName);
	Users save(Users user);


}
