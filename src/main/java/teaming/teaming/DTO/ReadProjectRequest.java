package teaming.teaming.DTO;

public record ReadProjectRequest(
        Long id,
        String title,
        String content
) {
}
