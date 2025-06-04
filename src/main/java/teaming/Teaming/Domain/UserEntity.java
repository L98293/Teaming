package teaming.Teaming.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; //아이디
    private String password; //비번
    private String githubId; //깃허브 아이디

    private String role; //역할

    @ManyToMany(mappedBy = "users")
    private List<Project> projects = new ArrayList<>();

    public void addProject(Project project) {
        this.projects.add(project);
        if (!project.getUsers().contains(this)) {
            project.getUsers().add(this);
        }
    }
}