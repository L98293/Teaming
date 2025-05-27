package teaming.teaming.DTO;

public record UpdateProjectRequest(
        Long id,
        String title,
        String content
) {
}
