package coms309.proj1.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import coms309.proj1.friend.FriendRelationship;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User implements UserDetails
{


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
	private Long userId;

	/**
	 * User chosen username to log in with
	 */
	private String username;

	private String email;

	/**
	 * Encrypted password of the user
	 */
	@JsonIgnore
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	private Boolean locked;

	private Boolean enabled;

	/**
	 * One instance of User can map to multiple instances of FriendRelationship
	 * -> One User row can map to multiple rows in the FriendRelationship table
	 *
	 * orphanRemoval means removing from collection friends will remove the row from
	 * 				 the friend relationship table.
	 */
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY,  orphanRemoval = true)// cascade=CascadeType.ALL, orphanRemoval = true
	//@JsonManagedReference
	@JsonIgnoreProperties("friends")
	//@JsonIgnore
	private List<FriendRelationship> friends = new ArrayList<FriendRelationship>();

	public User(String username, String email, String password, UserRole role) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.locked = false;
		this.enabled = false;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role.name());
		return Collections.singletonList(auth);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return this.userId + ": " + this.username + ", " + this.email;
	}


	/**
	 * @return true
	 */
	public boolean addFriendRelationship(FriendRelationship r) {
		return this.friends.add(r);
	}

	/**
	 * @return true if entity was contained in the list
	 */
	public boolean removeFriendRelationship(FriendRelationship r) {
		return this.friends.remove(r);
	}

	/**
	 * @return list of friend relationships the owner has
	 */
	public List<FriendRelationship> getFriendRelationships() {
		return this.friends;
	}

	/**
	 * @return list of users the owner is friends with
	 */
	public List<User> getFriends() {
		List<User> list = new ArrayList<User>();
		for (FriendRelationship friend : this.friends) {
			list.add(friend.getFriend());
		}
		return list;
	}

}