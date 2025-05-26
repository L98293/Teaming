package teaming.teaming.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message; // 신청할 때 쓰는 한마디

    private boolean accepted; // 수락 여부

    @ManyToOne
    private Member applicant; // 신청자

    @ManyToOne
    private Project project; // 어떤 프로젝트에 신청했는지
}
