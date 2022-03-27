package coms309.proj1.registration.token;

import com.fasterxml.jackson.annotation.JsonFormat;
import coms309.proj1.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

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
    private Long id;

    @Column(nullable = false)
    private String token;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date expiredAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public ConfirmationToken(String token,
                             Date createdAt,
                             Date expiredAt,
                             User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.confirmedAt = null;
        this.user = user;
    }
}
