package coms309.proj1.stat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.proj1.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_stats")
@Getter
@Setter
public class UserStats
{

	@Id
	@Column(name = "user_id")
	private Long id;


	@OneToOne
	@MapsId
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;

	private int level;
	private int experience;
	private int kills;
	private int wins;
	private int losses;

	public UserStats() {
		this.level = 1;
		this.experience = 0;
		this.kills = 0;
		this.wins = 0;
		this.losses = 0;

	}
	public UserStats(User user) {
		this();
		this.user = user;
	}

}
