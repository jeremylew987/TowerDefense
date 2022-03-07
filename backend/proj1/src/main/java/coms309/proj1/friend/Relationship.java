package coms309.proj1.friend;

import coms309.proj1.user.User;
import org.apache.tomcat.jni.Time;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "relationship")
public class Relationship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "relationship_id")
	private Integer relationshipId;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@ManyToOne
	@JoinColumn(name = "friend_id")
	private User friend;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;

	public Relationship() {
		this.createdDate = LocalDateTime.now();
		this.friend = null;
		this.owner = null;

	}
	public Relationship(User friend) {
		super();
		this.friend = friend;
	}
	public Relationship(User owner, User friend) {
		this.createdDate = LocalDateTime.now();
		this.friend = friend;
		this.owner = owner;
	}

	public Integer getId() {
		return relationshipId;
	}

	public void setId(Integer relationshipId) {
		this.relationshipId = relationshipId;
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

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}
}