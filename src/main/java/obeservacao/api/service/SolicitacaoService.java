package obeservacao.api.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import obeservacao.api.dto.SolicitacaoCreateDto;
import obeservacao.api.dto.SolicitacaoStatusHistoryResponseDto;
import obeservacao.api.dto.SolicitacaoUpdateDto;
import obeservacao.api.infra.exception.SolicitacaoException;
import obeservacao.api.model.Solicitacao;
import obeservacao.api.model.SolicitacaoStatusHistory;
import obeservacao.api.model.User;
import obeservacao.api.model.enums.SolicitacaoHistoryEventType;
import obeservacao.api.model.enums.StatusSolicitacao;
import obeservacao.api.model.enums.UserRole;
import obeservacao.api.repository.SolicitacaoRepository;
import obeservacao.api.repository.SolicitacaoStatusHistoryRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {

    private final SolicitacaoRepository repository;
    private final SolicitacaoStatusHistoryRepository statusHistoryRepository;

    @Transactional
    public Solicitacao criarAutenticada(SolicitacaoCreateDto dto, User user) {
        validarUsuarioAutenticado(user);
        if (user.getRole() != UserRole.USER) {
            throw new SolicitacaoException("Apenas usuarios com perfil adequado podem criar solicitacoes.");
        }

        Solicitacao solicitacao = new Solicitacao(
                dto.categoria(),
                dto.descricao(),
                dto.localizacao(),
                dto.prioridade(),
                user
        );
        Solicitacao criada = repository.save(solicitacao);
        registrarCriacao(criada, user, user.getName());
        return criada;
    }

    @Transactional
    public Solicitacao criarAnonima(SolicitacaoCreateDto dto) {
        Solicitacao solicitacao = new Solicitacao(
                dto.categoria(),
                dto.descricao(),
                dto.localizacao(),
                dto.prioridade(),
                null
        );
        Solicitacao criada = repository.save(solicitacao);
        registrarCriacao(criada, null, "Anonimo");
        return criada;
    }

    public List<Solicitacao> listarTodas() {
        return repository.findAll();
    }

    public Solicitacao buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitacao nao encontrada."));
    }

    public List<Solicitacao> buscarPorUsuario(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Solicitacao atualizar(UUID id, SolicitacaoUpdateDto dto, User user) {
        validarUsuarioAutenticado(user);
        if (user.getRole() != UserRole.ADMIN) {
            throw new SolicitacaoException("Apenas administradores podem atualizar solicitacoes.");
        }
        if (dto.status() == null) {
            throw new SolicitacaoException("O status da solicitacao e obrigatorio para atualizacao.");
        }

        Solicitacao solicitacao = buscarPorId(id);
        StatusSolicitacao statusAnterior = solicitacao.getStatus();
        if (statusAnterior == dto.status()) {
            return solicitacao;
        }

        LocalDateTime atualizadoEm = LocalDateTime.now();
        solicitacao.atualizarStatus(dto.status(), atualizadoEm);
        statusHistoryRepository.save(new SolicitacaoStatusHistory(
                solicitacao,
                user,
                user.getName(),
                SolicitacaoHistoryEventType.UPDATED,
                statusAnterior,
                dto.status(),
                atualizadoEm
        ));
        return repository.save(solicitacao);
    }

    public SolicitacaoStatusHistoryResponseDto buscarHistoricoStatus(UUID id, User user) {
        validarUsuarioAutenticado(user);
        Solicitacao solicitacao = buscarPorId(id);
        validarAcessoAoHistorico(solicitacao, user);

        List<SolicitacaoStatusHistory> history =
                statusHistoryRepository.findBySolicitacaoIdOrderByOccurredAtAscIdAsc(id);
        return new SolicitacaoStatusHistoryResponseDto(solicitacao, history);
    }

    public void deletar(UUID id, User user) {
        validarUsuarioAutenticado(user);
        if (user.getRole() != UserRole.USER) {
            throw new SolicitacaoException("A exclusao de solicitacoes esta disponivel apenas para usuarios comuns.");
        }

        Solicitacao solicitacao = buscarPorId(id);
        if (solicitacao.getUsuario() == null || !solicitacao.getUsuario().getId().equals(user.getId())) {
            throw new SolicitacaoException("Voce pode excluir apenas solicitacoes cadastradas em seu proprio usuario.");
        }
        if (solicitacao.getStatus() != StatusSolicitacao.ABERTO) {
            throw new SolicitacaoException("Apenas solicitacoes com status ABERTO podem ser excluidas.");
        }

        repository.delete(solicitacao);
    }

    private void validarUsuarioAutenticado(User user) {
        if (user == null || user.getId() == null) {
            throw new SolicitacaoException("Usuario autenticado invalido ou nao identificado.");
        }
    }

    private void validarAcessoAoHistorico(Solicitacao solicitacao, User user) {
        if (user.getRole() == UserRole.ADMIN) {
            return;
        }
        if (user.getRole() == UserRole.USER
                && solicitacao.getUsuario() != null
                && solicitacao.getUsuario().getId().equals(user.getId())) {
            return;
        }
        throw new AccessDeniedException("Voce nao tem permissao para consultar o historico desta solicitacao.");
    }

    private void registrarCriacao(Solicitacao solicitacao, User usuario, String actorName) {
        statusHistoryRepository.save(new SolicitacaoStatusHistory(
                solicitacao,
                usuario,
                actorName,
                SolicitacaoHistoryEventType.CREATED,
                null,
                StatusSolicitacao.ABERTO,
                solicitacao.getDataCriacao()
        ));
    }
}
