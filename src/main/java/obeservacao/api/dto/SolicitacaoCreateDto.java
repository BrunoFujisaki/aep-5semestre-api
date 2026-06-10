package obeservacao.api.dto;

import obeservacao.api.model.enums.*;

public record SolicitacaoCreateDto(
        Categoria categoria,
        String descricao,
        String localizacao,
        Prioridade prioridade,
        Boolean anonima
) {
}