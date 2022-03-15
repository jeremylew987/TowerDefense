package coms309.proj1.friend;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import coms309.proj1.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friendrelationship")
public class FriendRelationship
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "relationship_id")
	private Integer relationshipId;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	/*
	 * @ManyToOne  -> Several friend relationship rows can map to one user
	 * @JoinColumn -> specified ownership of the key i.e. FriendRelationship table will contain
	 * 			      a foreign key form the User table and the column name will be owner_id
	 * @JsonIgnore -> Avoids infinite loops when returning user/friend relationship object (FR -> user ->[phones->...)
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id", referencedColumnName="user_id")
	//@JsonIgnoreProperties("friends")
	//@JsonBackReference
	@JsonIgnore
	private User owner;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "friend_id", referencedColumnName="user_id")
	//@JsonIgnoreProperties("friends")
	//@JsonBackReference
	@JsonIgnore
	private User friend;


	// =============================== Constructors ================================== //


	public FriendRelationship() {
		this.createdDate = LocalDateTime.now();
		this.friend = null;
		this.owner = null;

	}
	public FriendRelationship(User owner, User friend) {
		this.createdDate = LocalDateTime.now();
		this.friend = friend;
		this.owner = owner;
	}

	// =============================== Getters & Setters ================================== //

	public Integer getId() {
		return relationshipId;
	}

	public void setId(Integer id) {
		this.relationshipId = id;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User user) {
		this.owner = user;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User user) {
		this.friend = user;
	}
}