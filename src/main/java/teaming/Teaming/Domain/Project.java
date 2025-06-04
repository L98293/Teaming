package teaming.Teaming.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private UserEntity leader;

    @ManyToMany
    private List<UserEntity> users = new ArrayList<>();

    public void addUser(UserEntity user) {
        this.users.add(user);
        if (!user.getProjects().contains(this)) {
            user.getProjects().add(this);
        }
    }
}
