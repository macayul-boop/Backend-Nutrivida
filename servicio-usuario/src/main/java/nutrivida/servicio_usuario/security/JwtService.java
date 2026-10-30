package nutrivida.servicio_usuario.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${spring.security.jwt.secret}")
    private String secretKey;

    @Value("${spring.security.jwt.expiration}")
    private long jwtExpiration;

    // 1. CREAR TOKEN (Se usa en Registro y Login)
    public String generateToken(String email, String rol, Integer id) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", List.of(rol)); // Guardamos el rol como lista ["ROLE_USER"]
        extraClaims.put("id", id);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(email) // El identificador principal
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. EXTRAER EMAIL
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 3. EXTRAER ROLES DEL TOKEN
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }

    @SuppressWarnings("unchecked")
    public Integer extractId(String token) {
        return extractClaim(token, claims -> claims.get("id", Integer.class));
    }

    // 4. VALIDAR TOKEN (Solo valida firma y tiempo, NADA de base de datos)
    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}