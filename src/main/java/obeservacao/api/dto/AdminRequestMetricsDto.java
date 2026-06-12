package obeservacao.api.dto;

import java.util.Map;

public record AdminRequestMetricsDto(
        int total,
        Map<String, Integer> byStatus,
        Cards cards
) {
    public record Cards(
            int abertas,
            int emTriagemOuExecucao,
            int resolvidasOuEncerradas
    ) {
    }
}
