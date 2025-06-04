package teaming.Teaming.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teaming.Teaming.Domain.Invite;
import teaming.Teaming.Domain.Project;
import teaming.Teaming.Domain.UserEntity;

import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

    // 특정 유저가 받은 초대 목록
    List<Invite> findByContributor(UserEntity contributor);

    // 중복 초대 여부 확인
    boolean existsByProjectAndContributor(Project project, UserEntity contributor);

    List<Invite> findByContributorAndAcceptedTrue(UserEntity contributor);

    // (선택) 내가 보낸 초대 목록 조회
//    List<Invite> findByMember(Member member);

    // (선택) 프로젝트 기준 초대 전체 보기
//    List<Invite> findByProject(Project project);
}
