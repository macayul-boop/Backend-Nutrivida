package nutrivida.servicio_datos_clinicos.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtParser parser;

    public JwtService(@Value("${spring.security.jwt.secret}") String clave){
        this.parser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(clave)))
                .build();
    }

    // VALIDA LA FIRMA Y LA EXPIRACION DEL TOKEN EMITIDO POR USUARIOS
    public Claims extractClaims(String token){
        return parser.parseClaimsJws(token).getBody();
    }
}