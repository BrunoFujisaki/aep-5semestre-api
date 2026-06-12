package obeservacao.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import obeservacao.api.model.enums.*;

public record SolicitacaoCreateDto(
        @NotNull(message = "categoria is required")
        Categoria categoria,

        @NotBlank(message = "descricao is required")
        String descricao,

        @NotBlank(message = "localizacao is required")
        String localizacao,

        @NotNull(message = "prioridade is required")
        Prioridade prioridade
) {
}
