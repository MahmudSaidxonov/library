package uz.lib.library.security;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.lib.library.model.User;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtService {
    @Value("${security.secret.key}")
    private String secretKey;

    private final Gson gson;

    public String generateToken(String user, List<String> roles) {

        return Jwts.builder()
                .setSubject(user)
                .claim("role", roles)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public String extractUsername(String authToken) {
        String json = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(authToken)
                .getBody()
                .getSubject();
        return gson.fromJson(json, User.class).getUsername();
    }

    public boolean validateToken(String token){
            return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
//                .getTime() < System.currentTimeMillis();
                .after(Date.from(Instant.now()));

    }


    public <T> T getClaim(String token, String claimName, Class<T> type){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(claimName, type);
    }

}
