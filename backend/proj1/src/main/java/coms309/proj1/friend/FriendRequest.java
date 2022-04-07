package coms309.proj1.friend;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import coms309.proj1.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friendrequest")
public class FriendRequest
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "request_id")
	private Integer requestId;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	/*
	 * @ManyToOne  -> Several friend request rows can map to one user
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sender_id", referencedColumnName="user_id")
	@JsonIgnoreProperties("friends")
//	@JsonIgnore
//	@JsonBackReference
	private User sender;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "receiver_id", referencedColumnName="user_id")
	@JsonIgnoreProperties("friends")
//	@JsonIgnore
//	@JsonBackReference
	private User receiver;


	// =============================== Constructors ================================== //


	public FriendRequest() {
		this.createdDate = LocalDateTime.now();
		this.receiver = null;
		this.sender = null;

	}
	public FriendRequest(User sender, User receiver) {
		this.createdDate = LocalDateTime.now();
		this.receiver = receiver;
		this.sender = sender;
	}

	// =============================== Getters & Setters ================================== //

	public Integer getId() {
		return requestId;
	}

	public void setId(Integer id) {
		this.requestId = id;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User user) {
		this.receiver = user;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User user) {
		this.sender = user;
	}
}