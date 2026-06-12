package obeservacao.api.repository.projection;

import obeservacao.api.model.enums.StatusSolicitacao;

public record StatusCountProjection(
        StatusSolicitacao status,
        long total
) {
}
