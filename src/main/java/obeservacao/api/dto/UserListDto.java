package obeservacao.api.dto;

import obeservacao.api.model.User;
import obeservacao.api.model.enums.UserRole;

public record UserListDto(
        String name,
        String email,
        UserRole role
) {
    public UserListDto(User user) {
        this(
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
