package dev.jdsoft.ordersassignment.security;

import dev.jdsoft.ordersassignment.persistence.dao.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultUserAuthenticationFilter extends OncePerRequestFilter {

    private static final String DEFAULT_EMAIL = "john.doe@example.com";

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        userRepository.findByEmail(DEFAULT_EMAIL).ifPresent(user -> {
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, List.of(SecurityRoles.USER, SecurityRoles.ADMIN));
            SecurityContextHolder.getContext().setAuthentication(auth);
        });

        filterChain.doFilter(request, response);
    }
}