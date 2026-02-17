package uz.lib.library.security;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import uz.lib.library.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthManager implements AuthenticationManager {

    private final JwtService jwtService;
    private final Gson gson;

    public Authentication authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String username;
        try {
            username = jwtService.extractUsername(token);
        } catch (Exception e) {
            username = null;
            System.out.println(e.getMessage());
        }

        if (username != null && jwtService.validateToken(token)){
            List<Object> role = jwtService.getClaim(token, "role", List.class);
            List<GrantedAuthority> roles = role.stream()
                    .map(s -> new SimpleGrantedAuthority(s.toString()))
                    .collect(Collectors.toList());
            User user = gson.fromJson(jwtService.getClaim(token, "sub", String.class), User.class);

            UsernamePasswordAuthenticationToken authenticatedUser =
                    new UsernamePasswordAuthenticationToken(user, null, roles);
            return authenticatedUser;
        }else {
            return null;
        }
    }
}

