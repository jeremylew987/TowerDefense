package coms309.proj1.user;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import coms309.proj1.Views;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@JsonView(Views.Summary.class)
public class UserDetailsImpl implements UserDetails {
	
	private User user;

	public UserDetailsImpl(User user) {
		this.user = user;
	}

	@Override
	@JsonView(Views.DetailedALL.class)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		UserRole role = user.getRole();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return authorities;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	@JsonView(Views.DetailedALL.class)
	public String getUsername() {
		return user.getUsername();
	}

	@JsonView(Views.DetailedALL.class)
	public String getEmail() { return user.getEmail(); }

	@JsonView(Views.DetailedALL.class)
	public Long getId() {
		return user.getUserId();
	}

	public User getUser() {return user;}

	@Override
	@JsonView(Views.Detailed.class)
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonView(Views.Detailed.class)
	public boolean isAccountNonLocked() {
		return !user.locked;
	}

	@Override
	@JsonView(Views.Detailed.class)
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonView(Views.Detailed.class)
	public boolean isEnabled() {
		return user.enabled;
	}

}
