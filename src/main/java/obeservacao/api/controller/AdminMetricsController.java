package obeservacao.api.controller;

import lombok.RequiredArgsConstructor;
import obeservacao.api.dto.AdminRequestMetricsDto;
import obeservacao.api.dto.AdminUserMetricsDto;
import obeservacao.api.service.AdminMetricsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/metrics")
@RequiredArgsConstructor
public class AdminMetricsController {

    private final AdminMetricsService adminMetricsService;

    @GetMapping("/requests")
    public ResponseEntity<AdminRequestMetricsDto> getRequestMetrics() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adminMetricsService.getRequestMetrics());
    }

    @GetMapping("/users")
    public ResponseEntity<AdminUserMetricsDto> getUserMetrics() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adminMetricsService.getUserMetrics());
    }
}
