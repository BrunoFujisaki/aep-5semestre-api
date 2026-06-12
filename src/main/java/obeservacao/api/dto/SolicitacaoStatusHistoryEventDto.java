package obeservacao.api.dto;

import obeservacao.api.model.SolicitacaoStatusHistory;
import obeservacao.api.model.enums.SolicitacaoHistoryEventType;
import obeservacao.api.model.enums.StatusSolicitacao;

import java.time.LocalDateTime;
import java.util.UUID;

public record SolicitacaoStatusHistoryEventDto(
        UUID id,
        SolicitacaoHistoryEventType eventType,
        LocalDateTime occurredAt,
        StatusSolicitacao fromStatus,
        StatusSolicitacao toStatus,
        SolicitacaoStatusHistoryActorDto actor
) {
    public SolicitacaoStatusHistoryEventDto(SolicitacaoStatusHistory history) {
        this(
                history.getId(),
                history.getEventType(),
                history.getOccurredAt(),
                history.getFromStatus(),
                history.getToStatus(),
                new SolicitacaoStatusHistoryActorDto(history.getUsuario(), history.getActorName())
        );
    }
}
