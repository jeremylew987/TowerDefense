package coms309.proj1.user;

import coms309.proj1.friend.Relationship;
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
public class User implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq"
    )
    @Column(name = "user_id")
    private Long userId;

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Boolean locked;
    private Boolean enabled;

    @OneToMany(mappedBy = "owner", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Relationship> friends;

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

    public void  setUsername(String username) {
        this.username = username;
    }
    public void  setEmail(String email) {
        this.email  = email;
    }
    public String  getEmail() {
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


    public Relationship addFriend(Relationship r) {
        r.setOwner(this);
        friends.add(r);
        return r;
    }

    public Relationship removeFriend(Relationship r) {

        if(friends.remove(r)) {
            return r;
        }
        return null;
    }

    public List<User> getFriends() {
        List<User> list = new ArrayList<User>();
        for (Relationship friend : friends) {
            list.add(friend.getFriend());
        }
        return list;
    }

}