package teaming.teaming.Controller;

import org.springframework.web.bind.annotation.*;
import teaming.teaming.DTO.CreateProjectRequest;
import teaming.teaming.DTO.ProjectResponse;
import teaming.teaming.DTO.ReadProjectRequest;
import teaming.teaming.DTO.UpdateProjectRequest;
import teaming.teaming.domain.Project;
import teaming.teaming.repository.ProjectRepository;
import teaming.teaming.ProjectService;

import java.util.List;

// @CrossOrigin(origins = "*")
// 이 위에 코드 ㅈㄴ위험함, 모든 도메인에서 온 요청 허용하는 코드
// 나중에 도메인 사서 넣기 전까지는 어쩔수 없이 5500으로 돌리고
// 서버는 따로 8080에서 돌릴 때만 써야할 듯
@RestController
@RequestMapping("/project")
public class ProjectController {
    private ProjectService projectService;
    private ProjectRepository projectRepository;

    // @Autowired대신 직접 필드 주입
    public ProjectController(ProjectService projectService, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    // CRUD중 C부분
    @PostMapping("/post") // 실제 경로: /project/post
    public void createProject(@RequestBody CreateProjectRequest request) {
        projectService.createProject(request);
    }

    // CRUD중 R부분 -> 프로젝트 전체 조회
    @GetMapping("")
    public List<ReadProjectRequest> getProjects() {
        return projectService.getProjects();
    }

    // CRUD중 R부분 -> 프로젝트 상세 조회
    @GetMapping("/{id}")
    public ProjectResponse getProject(@PathVariable Long id) {
        Project p = projectRepository.findById(id).orElseThrow(()
        -> new IllegalArgumentException("존재하지 않는 게시물 입니다."));
        return new ProjectResponse(p.getId(), p.getTitle(), p.getContent());
    }

    // CRUD중 U부분 -> 프로젝트 수정
    @PatchMapping("/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody UpdateProjectRequest request) {
        return projectService.updateProject(id, request);
    }

    // CRUD중 D부분 -> 프로젝트 삭제
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
    }
}
