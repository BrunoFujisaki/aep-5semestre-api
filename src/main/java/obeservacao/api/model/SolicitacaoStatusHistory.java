package obeservacao.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import obeservacao.api.model.enums.SolicitacaoHistoryEventType;
import obeservacao.api.model.enums.StatusSolicitacao;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "solicitacoes_status_history")
@Getter
@NoArgsConstructor
public class SolicitacaoStatusHistory {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "solicitacao_id", nullable = false)
    private Solicitacao solicitacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @Column(nullable = false)
    private String actorName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SolicitacaoHistoryEventType eventType;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSolicitacao toStatus;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    public SolicitacaoStatusHistory(Solicitacao solicitacao, User usuario, String actorName,
                                    SolicitacaoHistoryEventType eventType, StatusSolicitacao fromStatus,
                                    StatusSolicitacao toStatus, LocalDateTime occurredAt) {
        this.solicitacao = solicitacao;
        this.usuario = usuario;
        this.actorName = actorName;
        this.eventType = eventType;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.occurredAt = occurredAt;
    }
}
