package teaming.Teaming.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_manager")
    private UserEntity manager;

    @ManyToOne
    @JoinColumn(name = "project_contributer")
    private UserEntity contributor;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Getter
    @Setter
    private boolean accepted;

    public Invite() {
        this.accepted = false;
    }

    public Invite(UserEntity manager, UserEntity contributor, Project project) {
        this.manager = manager;
        this.contributor = contributor;
        this.project = project;
        this.accepted = false;
    }
}
