package obeservacao.api.dto;

import java.util.Map;

public record AdminUserMetricsDto(
        int total,
        Map<String, Integer> byRole,
        Cards cards
) {
    public record Cards(
            int administradores,
            int usuariosComuns
    ) {
    }
}
