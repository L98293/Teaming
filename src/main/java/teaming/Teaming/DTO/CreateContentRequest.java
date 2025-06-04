package teaming.Teaming.DTO;

public record CreateContentRequest(
//        Long id,
//        id는 DB에 값 들어가면 자동으로 추가됨 = Long id는 굳이 쓸모없음
        String title,
        String content
) {
}
