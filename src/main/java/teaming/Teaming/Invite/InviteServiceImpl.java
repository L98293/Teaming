package teaming.Teaming.Invite;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import teaming.Teaming.Domain.Invite;
import teaming.Teaming.Domain.UserEntity;
import teaming.Teaming.Repository.InviteRepository;
import teaming.Teaming.Repository.ProjectRepository;
import teaming.Teaming.Domain.Project;
import teaming.Teaming.Repository.UserEntityRepository;

@Service
@Qualifier("InviteServiceImpl")
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {
    private final InviteRepository inviteRepository;
    private final UserEntityRepository userEntityRepository;
    private final ProjectRepository projectRepository;


    @Override
    @Transactional // 실패 시 롤백
    public void sendInvite(Long managerId, Long contributerId, Long projectId) {
        // managerId 없으
        UserEntity manager = userEntityRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("해당 멤버를 찾을 수 없습니다."));
        // normalId 없으
        UserEntity normal = userEntityRepository.findById(contributerId)
                .orElseThrow(() -> new RuntimeException("해당 멤버를 찾을 수 없습니다."));
        // projectId 없으
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("해당 프로젝트를 찾을 수 없습니다."));


    }

    @Override
    @Transactional
    public void acceptInvite(Long inviteId) {
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new RuntimeException("초대 정보를 찾을 수 없습니다."));

        if (invite.isAccepted()) {
            throw new IllegalStateException("이미 수락한 초대입니다.");
        }

        invite.setAccepted(true);

        // 1. 연관관계에 필요한 정보 꺼내기
        UserEntity user = invite.getContributor();        // 초대된 유저 (contributer)
        Project project = invite.getProject();     // 초대한 프로젝트

        user.addProject(project);
    }
}
