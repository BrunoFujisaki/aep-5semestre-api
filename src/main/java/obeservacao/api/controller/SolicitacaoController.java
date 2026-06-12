package obeservacao.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import obeservacao.api.dto.SolicitacaoCreateDto;
import obeservacao.api.dto.SolicitacaoResponseDto;
import obeservacao.api.dto.SolicitacaoStatusHistoryResponseDto;
import obeservacao.api.dto.SolicitacaoUpdateDto;
import obeservacao.api.model.User;
import obeservacao.api.service.SolicitacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/solicitacoes")
@RequiredArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoService service;

    @PostMapping
    public ResponseEntity<SolicitacaoResponseDto> criar(@RequestBody @Valid SolicitacaoCreateDto dto,
                                                        @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SolicitacaoResponseDto(service.criarAutenticada(dto, user)));
    }

    @PostMapping("/anonimas")
    public ResponseEntity<SolicitacaoResponseDto> criarAnonima(@RequestBody @Valid SolicitacaoCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SolicitacaoResponseDto(service.criarAnonima(dto)));
    }

    @GetMapping
    public List<SolicitacaoResponseDto> listarTodas() {
        return service.listarTodas().stream()
                .map(SolicitacaoResponseDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    public SolicitacaoResponseDto buscarPorId(@PathVariable UUID id) {
        return new SolicitacaoResponseDto(service.buscarPorId(id));
    }

    @GetMapping("/{id}/status-history")
    public SolicitacaoStatusHistoryResponseDto buscarHistoricoStatus(@PathVariable UUID id,
                                                                     @AuthenticationPrincipal User user) {
        return service.buscarHistoricoStatus(id, user);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<SolicitacaoResponseDto> buscarPorUsuario(@PathVariable UUID usuarioId) {
        return service.buscarPorUsuario(usuarioId).stream()
                .map(SolicitacaoResponseDto::new)
                .toList();
    }

    @PutMapping("/{id}")
    public SolicitacaoResponseDto atualizar(@PathVariable UUID id,
                                            @RequestBody SolicitacaoUpdateDto dto,
                                            @AuthenticationPrincipal User user) {
        return new SolicitacaoResponseDto(service.atualizar(id, dto, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        service.deletar(id, user);
        return ResponseEntity.noContent().build();
    }
}
