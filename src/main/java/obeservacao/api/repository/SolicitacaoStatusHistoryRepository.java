package obeservacao.api.repository;

import obeservacao.api.model.SolicitacaoStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SolicitacaoStatusHistoryRepository extends JpaRepository<SolicitacaoStatusHistory, UUID> {

    List<SolicitacaoStatusHistory> findBySolicitacaoIdOrderByOccurredAtAscIdAsc(UUID solicitacaoId);
}
