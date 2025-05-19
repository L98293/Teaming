package teaming.teaming.DTO;

import teaming.teaming.Content;

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
