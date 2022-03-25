package coms309.proj1.user;

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

@NoArgsConstructor
@Entity
public class User{

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
    Boolean locked;
    Boolean enabled;

    public User(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = false;
        this.enabled = false;
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

    public void setPassword(String password) {
        this.password = password;
    }
    public void  setUsername(String username) {
        this.username = username;
    }
    public void  setEmail(String email) {
        this.email  = email;
    }

    @Override
    public String toString() {
        return this.userId + ": " + this.username + ", " + this.email;
    }

}