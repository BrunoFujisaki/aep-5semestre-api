package obeservacao.api.service;

import lombok.RequiredArgsConstructor;
import obeservacao.api.dto.AdminRequestMetricsDto;
import obeservacao.api.dto.AdminUserMetricsDto;
import obeservacao.api.model.enums.StatusSolicitacao;
import obeservacao.api.model.enums.UserRole;
import obeservacao.api.repository.SolicitacaoRepository;
import obeservacao.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminMetricsService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final UserRepository userRepository;

    public AdminRequestMetricsDto getRequestMetrics() {
        Map<String, Integer> byStatus = initStatusCounts();
        solicitacaoRepository.countGroupedByStatus()
                .forEach(result -> byStatus.put(result.status().name(), toInt(result.total())));

        int abertas = byStatus.get(StatusSolicitacao.ABERTO.name());
        int emTriagemOuExecucao = byStatus.get(StatusSolicitacao.TRIAGEM.name())
                + byStatus.get(StatusSolicitacao.EM_EXECUCAO.name());
        int resolvidasOuEncerradas = byStatus.get(StatusSolicitacao.RESOLVIDO.name())
                + byStatus.get(StatusSolicitacao.ENCERRADO.name());

        return new AdminRequestMetricsDto(
                byStatus.values().stream().mapToInt(Integer::intValue).sum(),
                byStatus,
                new AdminRequestMetricsDto.Cards(
                        abertas,
                        emTriagemOuExecucao,
                        resolvidasOuEncerradas
                )
        );
    }

    public AdminUserMetricsDto getUserMetrics() {
        Map<String, Integer> byRole = initRoleCounts();
        userRepository.countGroupedByRole()
                .forEach(result -> byRole.put(result.role().name(), toInt(result.total())));

        int administradores = byRole.get(UserRole.ADMIN.name());
        int usuariosComuns = byRole.get(UserRole.USER.name());

        return new AdminUserMetricsDto(
                byRole.values().stream().mapToInt(Integer::intValue).sum(),
                byRole,
                new AdminUserMetricsDto.Cards(
                        administradores,
                        usuariosComuns
                )
        );
    }

    private Map<String, Integer> initStatusCounts() {
        Map<String, Integer> counts = new LinkedHashMap<>();
        Arrays.stream(StatusSolicitacao.values())
                .forEach(status -> counts.put(status.name(), 0));
        return counts;
    }

    private Map<String, Integer> initRoleCounts() {
        Map<String, Integer> counts = new LinkedHashMap<>();
        Arrays.stream(UserRole.values())
                .forEach(role -> counts.put(role.name(), 0));
        return counts;
    }

    private int toInt(long value) {
        return Math.toIntExact(value);
    }
}
