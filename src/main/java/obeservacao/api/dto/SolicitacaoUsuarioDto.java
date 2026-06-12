package obeservacao.api.dto;

import obeservacao.api.model.User;

import java.util.UUID;

public record SolicitacaoUsuarioDto(
        UUID id,
        String name,
        String email
) {
    public SolicitacaoUsuarioDto(User user) {
        this(user.getId(), user.getName(), user.getEmail());
    }
}
