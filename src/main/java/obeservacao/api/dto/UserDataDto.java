package obeservacao.api.dto;

import obeservacao.api.model.User;

public record UserDataDto(
        String id,
        String name,
        String email,
        String role
) {
    public UserDataDto(User user) {
        this(
            user.getId().toString(),
            user.getName(),
            user.getEmail(),
            user.getRole().toString()
        );
    }
}
