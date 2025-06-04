package teaming.Teaming.DTO;

import teaming.Teaming.Content;

public record ContentResponse(
        Long id,
        String title,
        String content
) {
    public static ContentResponse of(Content content) {
        return new ContentResponse(
                content.getId(),
                content.getTitle(),
                content.getContent()
        );
    }
}
