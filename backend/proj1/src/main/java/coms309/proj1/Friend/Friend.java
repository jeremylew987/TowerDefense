package coms309.proj1.Friend;

import coms309.proj1.user.User;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "friend")
public class Friend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "friend_id")
	private Integer friendId;

	@Column(name = "created_date")
	private Date createdDate;


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_id", referencedColumnName = "user_id")
	User firstUser;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "friend_to_id", referencedColumnName = "user_id")
	User secondUser;

	@ManyToOne
	@JoinColumn(name = "user_user_id")
	private User user;

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Friend() {
	}

	public Integer getId() {
		return friendId;
	}

	public void setId(Integer friendId) {
		this.friendId = friendId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getFirstUser() {
		return firstUser;
	}

	public void setFirstUser(User firstUser) {
		this.firstUser = firstUser;
	}

	public User getSecondUser() {
		return secondUser;
	}

	public void setSecondUser(User secondUser) {
		this.secondUser = secondUser;
	}
}