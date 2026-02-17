package uz.lib.library.security;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.service.additional.AppStatusCodes;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
//    private final JwtService jwtService;
//    private final Gson gson;
    private final AuthManager authManager;
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String authToken = null;
        if(authorization!=null && authorization.startsWith("Bearer ")) {
            authToken = authorization.substring(7);
        }
        else {
            log.warn("Authorization header is empty or incorrect");
        }

        if (authToken != null) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(authToken, authToken);
            SecurityContextHolder.getContext().setAuthentication(
                    authManager
                            .authenticate(authenticationToken));

        }

        filterChain.doFilter(request,response);
    }
}
