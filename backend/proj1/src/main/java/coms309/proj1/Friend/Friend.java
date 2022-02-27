//package coms309.proj1.Friend;
//
//import coms309.proj1.user.User;
//
//import javax.persistence.*;
//import java.sql.Date;
//
//@Entity
//@Table(name = "friends")
//public class Friend {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = friend_id)
//	private Integer friendId;
//
//	@Column(name = "created_date")
//	private Date createdDate;
//
//
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "first_user_id", referencedColumnName = "id")
//	User firstUser;
//
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "second_user_id", referencedColumnName = "id")
//	User secondUser;
//
//	public Friend() {
//	}
//
//	public Integer getId() {
//		return friendId;
//	}
//
//	public void setId(Integer friendId) {
//		this.friendId = friendId;
//	}
//
//	public Date getCreatedDate() {
//		return createdDate;
//	}
//
//	public void setCreatedDate(Date createdDate) {
//		this.createdDate = createdDate;
//	}
//
//	public User getFirstUser() {
//		return firstUser;
//	}
//
//	public void setFirstUser(User firstUser) {
//		this.firstUser = firstUser;
//	}
//
//	public User getSecondUser() {
//		return secondUser;
//	}
//
//	public void setSecondUser(User secondUser) {
//		this.secondUser = secondUser;
//	}
//}