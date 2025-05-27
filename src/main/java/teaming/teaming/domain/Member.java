package teaming.teaming.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;   // 닉네임
    private String email;
    private String password;

    @ElementCollection
    private List<String> techStack;  // ex. ["Java", "React"]

    @OneToMany(mappedBy = "leader")
    private List<Project> myProjects = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "member_joined_project",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> joinedProjects = new ArrayList<>(); // ✅ 요거!

    public Member() {}

    public Member(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}