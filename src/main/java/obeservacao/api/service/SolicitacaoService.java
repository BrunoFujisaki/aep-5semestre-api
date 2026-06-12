package obeservacao.api.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import obeservacao.api.dto.SolicitacaoCreateDto;
import obeservacao.api.dto.SolicitacaoUpdateDto;
import obeservacao.api.infra.exception.SolicitacaoException;
import obeservacao.api.model.Solicitacao;
import obeservacao.api.model.User;
import obeservacao.api.model.enums.UserRole;
import obeservacao.api.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {

    private final SolicitacaoRepository repository;

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
        return repository.save(solicitacao);
    }

    public Solicitacao criarAnonima(SolicitacaoCreateDto dto) {
        Solicitacao solicitacao = new Solicitacao(
                dto.categoria(),
                dto.descricao(),
                dto.localizacao(),
                dto.prioridade(),
                null
        );
        return repository.save(solicitacao);
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

    public Solicitacao atualizar(UUID id, SolicitacaoUpdateDto dto, User user) {
        validarUsuarioAutenticado(user);
        if (user.getRole() != UserRole.ADMIN) {
            throw new SolicitacaoException("Apenas administradores podem atualizar solicitacoes.");
        }
        if (dto.status() == null) {
            throw new SolicitacaoException("O status da solicitacao e obrigatorio para atualizacao.");
        }

        Solicitacao solicitacao = buscarPorId(id);
        solicitacao.atualizarStatus(dto.status());
        return repository.save(solicitacao);
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

        repository.delete(solicitacao);
    }

    private void validarUsuarioAutenticado(User user) {
        if (user == null || user.getId() == null) {
            throw new SolicitacaoException("Usuario autenticado invalido ou nao identificado.");
        }
    }
}
