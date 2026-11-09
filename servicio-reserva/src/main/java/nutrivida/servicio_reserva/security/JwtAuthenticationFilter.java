package nutrivida.servicio_reserva.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        System.out.println("\n========== JWT DEBUG ==========");
        System.out.println("URL: " + request.getRequestURI());
        System.out.println("Método: " + request.getMethod());
        System.out.println("Authorization recibido: " + (authHeader != null));

        // Comprobar encabezado Authorization
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            System.out.println("ERROR: No existe un token Bearer válido.");
            System.out.println("================================\n");

            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        // Validar token
        boolean tokenValido = jwtService.isTokenValid(jwt);

        System.out.println("Token válido: " + tokenValido);

        if (tokenValido) {

            String email = jwtService.extractUsername(jwt);
            Integer id = jwtService.extractId(jwt);
            List<String> roles = jwtService.extractRoles(jwt);

            System.out.println("Email: " + email);
            System.out.println("ID usuario: " + id);
            System.out.println("Roles extraídos del JWT: " + roles);

            // Convertir roles en autoridades de Spring Security
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .flatMap(role -> {
                        String cleanRole = role.trim();

                        if (cleanRole.startsWith("ROLE_")) {
                            return Stream.of(
                                    new SimpleGrantedAuthority(cleanRole),
                                    new SimpleGrantedAuthority(
                                            cleanRole.replace("ROLE_", "")
                                    )
                            );
                        } else {
                            return Stream.of(
                                    new SimpleGrantedAuthority(cleanRole),
                                    new SimpleGrantedAuthority("ROLE_" + cleanRole)
                            );
                        }
                    })
                    .collect(Collectors.toList());

            System.out.println("Authorities generadas: " + authorities);

            // Crear autenticación
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            id,
                            null,
                            authorities
                    );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);

            System.out.println("Autenticación establecida correctamente.");
            System.out.println(
                    "Authorities en SecurityContext: " +
                            SecurityContextHolder.getContext()
                                    .getAuthentication()
                                    .getAuthorities()
            );

        } else {
            System.out.println("ERROR: El JWT no es válido.");
        }

        System.out.println("================================\n");

        filterChain.doFilter(request, response);
    }
}
