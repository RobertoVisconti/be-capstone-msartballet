package robertovisconti.be_capstone_msartballet.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.exceptions.UnauthorizedException;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenToolkit {

    private String secret;

    public TokenToolkit(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String tokenGenerator(Utente utente) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .subject(String.valueOf(utente.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public UUID extractId(String token) {
        return UUID.fromString(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(token).getPayload().getSubject());
    }

    public void tokenVerify(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Il token di login ha avuto un problema, per favore rieffetturare il login");
        }
    }
}
