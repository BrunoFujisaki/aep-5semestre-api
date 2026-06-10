package obeservacao.api.repository;

import obeservacao.api.model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, UUID> {

    Optional<Solicitacao> findByProtocolo(String protocolo);

    List<Solicitacao> findByUsuarioId(UUID usuarioId);

}