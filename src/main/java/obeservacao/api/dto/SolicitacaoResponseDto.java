package obeservacao.api.dto;

import obeservacao.api.model.Solicitacao;
import obeservacao.api.model.User;
import obeservacao.api.model.enums.Categoria;
import obeservacao.api.model.enums.Prioridade;
import obeservacao.api.model.enums.StatusSolicitacao;

import java.time.LocalDateTime;
import java.util.UUID;

public record SolicitacaoResponseDto(
        UUID id,
        String protocolo,
        Categoria categoria,
        String descricao,
        String localizacao,
        Prioridade prioridade,
        StatusSolicitacao status,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        SolicitacaoUsuarioDto usuario
) {
    public SolicitacaoResponseDto(Solicitacao solicitacao) {
        this(
                solicitacao.getId(),
                solicitacao.getProtocolo(),
                solicitacao.getCategoria(),
                solicitacao.getDescricao(),
                solicitacao.getLocalizacao(),
                solicitacao.getPrioridade(),
                solicitacao.getStatus(),
                solicitacao.getDataCriacao(),
                solicitacao.getDataAtualizacao(),
                mapUsuario(solicitacao.getUsuario())
        );
    }

    private static SolicitacaoUsuarioDto mapUsuario(User user) {
        return user == null ? null : new SolicitacaoUsuarioDto(user);
    }
}
