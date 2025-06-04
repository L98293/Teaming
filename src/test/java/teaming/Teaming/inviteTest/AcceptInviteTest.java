package teaming.Teaming.inviteTest;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import teaming.Teaming.Domain.Invite;
import teaming.Teaming.Domain.Project;
import teaming.Teaming.Domain.UserEntity;
import teaming.Teaming.Invite.InviteService;
import teaming.Teaming.Invite.InviteServiceImpl;
import teaming.Teaming.Repository.InviteRepository;
import teaming.Teaming.Repository.ProjectRepository;
import teaming.Teaming.Repository.UserEntityRepository;
import teaming.Teaming.Service.UserService;

@SpringBootTest
public class AcceptInviteTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    @Qualifier("InviteServiceImpl")
    private InviteService inviteService;

    @Transactional
    @Test
    public void testAcceptInvite() {
        UserEntity manager = new UserEntity();
        manager.setUsername("manager");
        userEntityRepository.save(manager);

        UserEntity contributor = new UserEntity();
        contributor.setUsername("contributor");
        userEntityRepository.save(contributor);

        Project project = new Project();
        project.setTitle("Test Project");
        projectRepository.save(project);

        Invite invite = new Invite(manager, contributor, project);
        inviteRepository.save(invite);

        inviteService.acceptInvite(invite.getId());
        // 하드코딩하면 안됨

        Assertions.assertThat(contributor.getProjects()).contains(project);
        // 수락한 project가 contributor에 있나

        System.out.println(contributor.getProjects());

        System.out.println(project.getUsers());


    }
}
