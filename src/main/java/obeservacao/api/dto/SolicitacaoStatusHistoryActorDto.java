package obeservacao.api.dto;

import obeservacao.api.model.User;

import java.util.UUID;

public record SolicitacaoStatusHistoryActorDto(
        UUID id,
        String name
) {
    public SolicitacaoStatusHistoryActorDto(User user, String actorName) {
        this(user == null ? null : user.getId(), actorName);
    }
}
