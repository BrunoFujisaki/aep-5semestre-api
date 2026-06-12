package obeservacao.api.dto;

import obeservacao.api.model.Solicitacao;
import obeservacao.api.model.SolicitacaoStatusHistory;
import obeservacao.api.model.enums.StatusSolicitacao;

import java.util.List;
import java.util.UUID;

public record SolicitacaoStatusHistoryResponseDto(
        UUID requestId,
        String protocolo,
        StatusSolicitacao currentStatus,
        List<SolicitacaoStatusHistoryEventDto> events
) {
    public SolicitacaoStatusHistoryResponseDto(Solicitacao solicitacao, List<SolicitacaoStatusHistory> history) {
        this(
                solicitacao.getId(),
                solicitacao.getProtocolo(),
                solicitacao.getStatus(),
                history.stream()
                        .map(SolicitacaoStatusHistoryEventDto::new)
                        .toList()
        );
    }
}
