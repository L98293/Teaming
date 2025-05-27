package teaming.teaming.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teaming.teaming.Domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
