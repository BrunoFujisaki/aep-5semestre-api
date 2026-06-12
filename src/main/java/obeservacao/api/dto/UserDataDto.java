package obeservacao.api.dto;

import obeservacao.api.model.User;

public record UserDataDto(
        String name,
        String email
) {
    public UserDataDto(User user) {
        this(
            user.getName(),
            user.getEmail()
        );
    }
}
