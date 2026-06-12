package obeservacao.api.dto;

import obeservacao.api.model.enums.Categoria;
import obeservacao.api.model.enums.Prioridade;
import obeservacao.api.model.enums.StatusSolicitacao;

public record SolicitacaoUpdateDto(
        StatusSolicitacao status
) {}
