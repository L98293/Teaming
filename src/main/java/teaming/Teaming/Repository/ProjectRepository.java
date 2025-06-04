package teaming.Teaming.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teaming.Teaming.Domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
