package com.jminardi.user;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Simple object representing a user.
 * @author James Minardi
 */
@Entity
@Table(name= "users")
public class Users
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;

	@Column(name = "username")
	@NotFound(action = NotFoundAction.IGNORE)
	private String username;

	@Column(name = "password")
	@NotFound(action = NotFoundAction.IGNORE)
	private String password;

	@Column(name = "first_name")
	@NotFound(action = NotFoundAction.IGNORE)
	private String first_name;

	@Column(name = "last_name")
	@NotFound(action = NotFoundAction.IGNORE)
	private String last_name;

	public Integer getId()
	{
		return this.id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return this.username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
}
