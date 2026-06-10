package obeservacao.api.dto;

import obeservacao.api.model.enums.Categoria;
import obeservacao.api.model.enums.Prioridade;
import obeservacao.api.model.enums.StatusSolicitacao;

public record SolicitacaoUpdateDto(
        Categoria categoria,
        String descricao,
        String localizacao,
        Prioridade prioridade,
        StatusSolicitacao status,
        Boolean anonima
) {}
