package obeservacao.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import obeservacao.api.dto.AuthDto;
import obeservacao.api.dto.UserCreateDto;
import obeservacao.api.dto.UserDataDto;
import obeservacao.api.infra.security.TokenJwtDTO;
import obeservacao.api.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDataDto> signUp(@RequestBody @Valid UserCreateDto dto) throws BadRequestException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.signUpUser(dto));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenJwtDTO> signIn(@RequestBody @Valid AuthDto dto) throws BadRequestException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.signInUser(dto));
    }
}
