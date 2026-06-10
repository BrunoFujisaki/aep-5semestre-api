package obeservacao.api.controller;

import lombok.RequiredArgsConstructor;
import obeservacao.api.dto.SolicitacaoCreateDto;
import obeservacao.api.dto.SolicitacaoUpdateDto;
import obeservacao.api.model.Solicitacao;
import obeservacao.api.service.SolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/solicitacoes")
@RequiredArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoService service;

    @PostMapping
    public ResponseEntity<String> criar(@RequestBody SolicitacaoCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Necessário vincular usuário autenticado no POST.");
    }

    @GetMapping
    public List<Solicitacao> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public Solicitacao buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Solicitacao> buscarPorUsuario(@PathVariable UUID usuarioId) {
        return service.buscarPorUsuario(usuarioId);
    }

    @PutMapping("/{id}")
    public Solicitacao atualizar(@PathVariable UUID id, @RequestBody SolicitacaoUpdateDto dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
