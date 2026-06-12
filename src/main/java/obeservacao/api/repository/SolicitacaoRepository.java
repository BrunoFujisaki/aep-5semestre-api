package obeservacao.api.repository;

import obeservacao.api.model.Solicitacao;
import obeservacao.api.repository.projection.StatusCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, UUID> {

    Optional<Solicitacao> findByProtocolo(String protocolo);

    List<Solicitacao> findByUsuarioId(UUID usuarioId);

    @Query("""
            select new obeservacao.api.repository.projection.StatusCountProjection(s.status, count(s))
            from Solicitacao s
            group by s.status
            """)
    List<StatusCountProjection> countGroupedByStatus();

}
