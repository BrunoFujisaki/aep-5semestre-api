package obeservacao.api.dto;

public record UserCreateDto(
        String name,
        String email,
        String password
) {
}
