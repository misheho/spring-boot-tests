package com.example.api_gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private static final String AUTH_HEADER = "Authorization";

    @Value("${security.jwt.secret:changeit-changeit-changeit-changeit}")
    private String jwtSecret;

    // package-visible constructor for tests
    AuthenticationFilter(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public AuthenticationFilter() {
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // Skip authentication for certain paths
        if (path.contains("/public") ||
                path.equals("/health") ||
                path.startsWith("/spb/")) {
            return chain.filter(exchange);
        }

        // Check for auth header
        String authHeader = request.getHeaders().getFirst(AUTH_HEADER);
        if (authHeader == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

        Claims claims = validateTokenAndGetClaims(token);
        if (claims == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String subject = claims.getSubject();

        // Add headers to downstream services (avoid forwarding raw token)
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Authenticated", "true")
                .header("X-User-Id", subject == null ? "" : subject)
                .build();

        ServerWebExchange mutated = exchange.mutate()
                .request(modifiedRequest)
                .build();

        return chain.filter(mutated);
    }

    @Override
    public int getOrder() {
        return -1; // High priority, executed early
    }

    private Claims validateTokenAndGetClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}