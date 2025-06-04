package teaming.Teaming.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teaming.Teaming.Domain.Project;
import teaming.Teaming.Domain.UserEntity;
import teaming.Teaming.Repository.UserEntityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserEntityRepository userEntityRepository;

    // 유저가 하고 있는 프로젝트 목록 조회
    public List<Project> getUserProjects(Long userId) {
        UserEntity user = userEntityRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));
        return user.getProjects();
    }
}