package coms309.proj1.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import coms309.proj1.Views;
import coms309.proj1.friend.FriendRequest;
import coms309.proj1.friend.Friendship;
import coms309.proj1.user.stat.UserStats;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonView(Views.Summary.class)
@NoArgsConstructor
@Entity
public class User{

	/**
	 * Id marks userId as primary key for user table
	 * GeneratedValue generates a value if not present. Starts from 1 and increments for each user stored in user_seq table
	 */
	@Id
	@SequenceGenerator(
			name = "user_seq",
			sequenceName = "user_seq",
			allocationSize = 1)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "user_seq")
	@Column(name = "user_id")
	@JsonView(Views.Summary.class)
	private Long userId;

	/**
	 * User chosen username to log in with
	 */
	private String username;

	@JsonView(Views.Detailed.class)
	private String email;

	/**
	 * Encrypted password of the user
	 */
	@JsonIgnore
	private String password;

	@Enumerated(EnumType.STRING)
	@JsonView(Views.Detailed.class)
    private UserRole role;

	@JsonView(Views.DetailedALL.class)
	Boolean locked;

	@JsonView(Views.DetailedALL.class)
	Boolean enabled;

	/**
	 * One instance of User can map to multiple instances of FriendRelationship
	 * -> One User row can map to multiple rows in the FriendRelationship table
	 *
	 * orphanRemoval means removing from collection friends will remove the row from
	 * 				 the friend relationship table.
	 */
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER,  orphanRemoval = true)
	@JsonIgnoreProperties("friends")
	@JsonView({Views.DetailedALL.class, Views.SummaryWithFriends.class})
	private List<Friendship> friends = new ArrayList<Friendship>();

	@OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY,  orphanRemoval = true)
	@JsonIgnoreProperties("receivedFriendRequests")
	@JsonView({Views.DetailedALL.class, Views.SummaryWithFriends.class})
	private List<FriendRequest> receivedFriendRequests = new ArrayList<FriendRequest>();

	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY,  orphanRemoval = true)
	@JsonIgnoreProperties("sentFriendRequests")
	@JsonView({Views.DetailedALL.class, Views.SummaryWithFriends.class})
	private List<FriendRequest> sentFriendRequests = new ArrayList<FriendRequest>();

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	@JsonView({Views.DetailedALL.class, Views.SummaryWithStats.class})
	private UserStats stats;

	public User(String username, String email, String password, UserRole role) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.locked = false;
		this.enabled = false;
		this.stats = new UserStats(this);
	}

    public Long getUserId() {
        return this.userId;
    }
    public String getPassword() {
        return this.password;
    }
    public String getUsername() {
        return this.username;
    }
    public String  getEmail() {
        return this.email;
    }
    public UserRole getRole() { return this.role; }
	public UserStats getStats() {
		return stats;
	}

	public void setPassword(String password) {
        this.password = password;
    }
    public void  setUsername(String username) {
        this.username = username;
    }
    public void  setEmail(String email) {
        this.email  = email;
    }

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;}

	public void setStats(UserStats stats) {
		this.stats = stats;

	}

	@Override
	public String toString() {
		return this.userId + ": " + this.username + ", " + this.email;
	}


	/**
	 * @return true
	 */
	public boolean addFriendship(Friendship r) {
		return this.friends.add(r);
	}

	/**
	 * @return true if entity was contained in the list
	 */
	public boolean removeFriendship(Friendship r) {
		return this.friends.remove(r);
	}

	/**
	 * @return list of friend relationships the owner has
	 */
	@JsonView(Views.SummaryWithFriendsALL.class)
	public List<Friendship> getFriendships() {
		return this.friends;
	}

	/**
	 * @return list of users the owner is friends with
	 */
	public List<User> getFriends() {
		List<User> list = new ArrayList<User>();
		for (Friendship friend : this.friends) {
			list.add(friend.getFriend());
		}
		return list;
	}

	public boolean addSentFriendRequest(FriendRequest fr) {
		return this.sentFriendRequests.add(fr);
	}

	public boolean addReceivedFriendRequest(FriendRequest fr) {
		return this.sentFriendRequests.add(fr);
	}

	public List<FriendRequest> getSentFriendRequests() {
		return this.sentFriendRequests;
	}

	public List<FriendRequest> getReceivedFriendRequests() {
		return this.receivedFriendRequests;
	}

	public boolean removeSentFriendRequest(FriendRequest fr) {
		return this.sentFriendRequests.remove(fr);
	}

	public boolean removeReceivedFriendRequest(FriendRequest fr) {
		return this.receivedFriendRequests.remove(fr);
	}

}