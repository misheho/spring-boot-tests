package com.example.api_gateway;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationFilterTest {

    private final String testSecret = "test-secret-test-secret-test-secret-1234";

    @Test
    public void missingHeaderReturns401() {
        AuthenticationFilter filter = new AuthenticationFilter(testSecret);

        MockServerHttpRequest request = MockServerHttpRequest.get("/").build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        AtomicBoolean called = new AtomicBoolean(false);
        Mono<Void> result = filter.filter(exchange, ex -> {
            called.set(true);
            return Mono.empty();
        });

        result.block();

        assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(called.get()).isFalse();
    }

    @Test
    public void validTokenPassesThrough() {
        AuthenticationFilter filter = new AuthenticationFilter(testSecret);

        String token = Jwts.builder()
                .setSubject("testuser")
                .signWith(Keys.hmacShaKeyFor(testSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        MockServerHttpRequest request = MockServerHttpRequest.get("/")
                .header("Authorization", "Bearer " + token)
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        AtomicBoolean called = new AtomicBoolean(false);
        Mono<Void> result = filter.filter(exchange, ex -> {
            called.set(true);
            return Mono.empty();
        });

        result.block();

        assertThat(exchange.getResponse().getStatusCode()).isNull();
        assertThat(called.get()).isTrue();
    }

    @Test
    public void skippedRoutePassesWithoutAuthHeader() {
        AuthenticationFilter filter = new AuthenticationFilter(testSecret);

        MockServerHttpRequest request = MockServerHttpRequest.get("/spb/api/penguins")
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        AtomicBoolean called = new AtomicBoolean(false);
        Mono<Void> result = filter.filter(exchange, ex -> {
            called.set(true);
            return Mono.empty();
        });

        result.block();

        assertThat(exchange.getResponse().getStatusCode()).isNull();
        assertThat(called.get()).isTrue();
    }
}
