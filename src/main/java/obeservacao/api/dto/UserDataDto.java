package obeservacao.api.dto;

import obeservacao.api.model.User;
import obeservacao.api.model.enums.UserRole;

import java.util.UUID;

public record UserDataDto(
        UUID id,
        String name,
        String email,
        UserRole role
) {
    public UserDataDto(User user) {
        this(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole()
        );
    }
}
