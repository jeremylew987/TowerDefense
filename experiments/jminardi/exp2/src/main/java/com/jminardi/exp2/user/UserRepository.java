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
	Optional<Users> findByUsername(@Param("username") String username);
	Optional<Users> findByPassword(@Param("password") String password);
	Optional<Users> findByFirst_name(@Param("first_name") String first_name);
	Optional<Users> findByLast_name(@Param("last_name") String last_name);
	Users save (Users user);


}
