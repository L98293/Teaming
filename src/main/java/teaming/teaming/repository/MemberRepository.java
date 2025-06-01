package Teaming.Teaming.Repository;

import Teaming.Teaming.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
