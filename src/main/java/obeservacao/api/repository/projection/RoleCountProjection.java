package obeservacao.api.repository.projection;

import obeservacao.api.model.enums.UserRole;

public record RoleCountProjection(
        UserRole role,
        long total
) {
}
