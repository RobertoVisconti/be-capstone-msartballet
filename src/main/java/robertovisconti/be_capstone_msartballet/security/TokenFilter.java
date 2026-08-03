package robertovisconti.be_capstone_msartballet.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.exceptions.UnauthorizedException;
import robertovisconti.be_capstone_msartballet.repositories.utenti.UtenteRepository;

import java.io.IOException;
import java.util.UUID;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private HandlerExceptionResolver resolver;
    private TokenToolkit tokenToolkit;
    private UtenteRepository utenteRepository;

    public TokenFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
                       TokenToolkit tokenToolkit,
                       UtenteRepository utenteRepository) {
        this.resolver = resolver;
        this.tokenToolkit = tokenToolkit;
        this.utenteRepository = utenteRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Token mancante o formato non valido. Atteso: 'Bearer <token>'");
            }

            String token = authHeader.substring(7);
            tokenToolkit.tokenVerify(token);

            UUID idUtente = tokenToolkit.extractId(token);
            Utente utente = utenteRepository.findById(idUtente)
                    .orElseThrow(() -> new UnauthorizedException("Utente associato al token non trovato"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    utente, null, utente.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            resolver.resolveException(request, response, null, ex);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/auth/") && !path.startsWith("/auth/admin/");
    }
}
