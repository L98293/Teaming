package teaming.teaming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teaming.teaming.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
