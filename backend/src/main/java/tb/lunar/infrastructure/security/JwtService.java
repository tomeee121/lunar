package tb.lunar.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
    private final Key key;
    private final long ttlMs;

    public JwtService(@Value("${app.jwt.secret:dev-secret-please-change}") String secret,
                      @Value("${app.jwt.ttl-ms:86400000}") long ttlMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.ttlMs = ttlMs;
    }

    public String generate(String username) {
        var now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ttlMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String username(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
