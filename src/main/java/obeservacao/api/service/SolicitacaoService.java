package obeservacao.api.service;

import lombok.RequiredArgsConstructor;
import obeservacao.api.dto.SolicitacaoCreateDto;
import obeservacao.api.dto.SolicitacaoUpdateDto;
import obeservacao.api.model.Solicitacao;
import obeservacao.api.model.User;
import obeservacao.api.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {

    private final SolicitacaoRepository repository;

    public Solicitacao criar(SolicitacaoCreateDto dto, User user) {
        Solicitacao s = new Solicitacao();
        s.setCategoria(dto.categoria());
        s.setDescricao(dto.descricao());
        s.setLocalizacao(dto.localizacao());
        s.setPrioridade(dto.prioridade());
        s.setAnonima(Boolean.TRUE.equals(dto.anonima()));
        if (!Boolean.TRUE.equals(dto.anonima())) s.setUsuario(user);
        return repository.save(s);
    }

    public List<Solicitacao> listarTodas() {
        return repository.findAll();
    }

    public Solicitacao buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Solicitacao> buscarPorUsuario(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public Solicitacao atualizar(UUID id, SolicitacaoUpdateDto dto) {
        Solicitacao s = buscarPorId(id);
        if (dto.categoria() != null) s.setCategoria(dto.categoria());
        if (dto.descricao() != null) s.setDescricao(dto.descricao());
        if (dto.localizacao() != null) s.setLocalizacao(dto.localizacao());
        if (dto.prioridade() != null) s.setPrioridade(dto.prioridade());
        if (dto.status() != null) s.setStatus(dto.status());
        if (dto.anonima() != null) s.setAnonima(dto.anonima());
        return repository.save(s);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}
