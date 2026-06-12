package obeservacao.api.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import obeservacao.api.dto.AuthDto;
import obeservacao.api.dto.UserCreateDto;
import obeservacao.api.dto.UserDataDto;
import obeservacao.api.infra.exception.UserException;
import obeservacao.api.infra.security.TokenJwtDTO;
import obeservacao.api.infra.security.TokenService;
import obeservacao.api.model.User;
import obeservacao.api.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager manager;
    private final TokenService tokenService;


    public TokenJwtDTO signInUser(AuthDto dto) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            var authentication = manager.authenticate(authToken);
            return tokenService.genToken((User) authentication.getPrincipal());
        } catch (RuntimeException ex) {
            throw new UserException("Nao foi possivel realizar o login. Verifique as credenciais informadas.");
        }
    }

    public UserDataDto signUpUser(@Valid UserCreateDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new UserException("O e-mail informado ja esta em uso.");
        }
        var passwordHash = new BCryptPasswordEncoder().encode(dto.password());
        return new UserDataDto(userRepository.save(new User(dto, passwordHash)));
    }
}
