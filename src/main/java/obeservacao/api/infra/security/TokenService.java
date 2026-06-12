package obeservacao.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import obeservacao.api.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${security.token.secret}")
    private String secret;

    public TokenJwtDTO genToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return new TokenJwtDTO(JWT.create()
                    .withIssuer("API Observacao")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId().toString())
                    .withClaim("name", user.getName())
                    .withClaim("role", user.getRole().toString())
                    .withExpiresAt(genExpirateDateTime())
                    .sign(algorithm));
        } catch (JWTCreationException exception){
            throw new RuntimeException("failed gen token");
        }

    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API Observacao")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("expired or invalid token");
        }
    }

    private Instant genExpirateDateTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
