package teaming.teaming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teaming.teaming.domain.Application;

public interface Repository extends JpaRepository<Application, Long> {
}