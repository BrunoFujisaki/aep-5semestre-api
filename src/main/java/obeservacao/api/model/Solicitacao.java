package obeservacao.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import obeservacao.api.model.enums.Categoria;
import obeservacao.api.model.enums.Prioridade;
import obeservacao.api.model.enums.StatusSolicitacao;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "solicitacoes")
@Getter
@Setter
@NoArgsConstructor
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String protocolo;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String localizacao;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    @ManyToOne
    private User usuario;

    private Boolean anonima;

    @PrePersist
    void pre() {
        protocolo = UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        status = StatusSolicitacao.ABERTO;
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }
}