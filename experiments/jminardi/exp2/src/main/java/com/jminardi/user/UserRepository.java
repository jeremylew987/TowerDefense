package com.jminardi.user;

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
	Optional<Users> findByUsername(@Param("username") String username);
	Users save (Users user);


}
