package teaming.Teaming.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teaming.Teaming.Domain.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

}
