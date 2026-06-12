package obeservacao.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import obeservacao.api.model.enums.Categoria;
import obeservacao.api.model.enums.Prioridade;
import obeservacao.api.model.enums.StatusSolicitacao;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "solicitacoes")
@Getter
@NoArgsConstructor
public class Solicitacao {

    @Id
    @UuidGenerator
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

    public Solicitacao(Categoria categoria, String descricao, String localizacao,
                       Prioridade prioridade, User usuario) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.prioridade = prioridade;
        this.usuario = usuario;
        this.protocolo = UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
        this.status = StatusSolicitacao.ABERTO;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = this.dataCriacao;
    }

    public void atualizarStatus(StatusSolicitacao status) {
        this.status = status;
        this.dataAtualizacao = LocalDateTime.now();
    }
}
