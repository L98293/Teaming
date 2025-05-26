package teaming.teaming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teaming.teaming.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
