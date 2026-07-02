package com.example.api_gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RateLimitingFilter implements GlobalFilter, Ordered {

    private final RateLimitConfig rateLimitConfig;

    public RateLimitingFilter(RateLimitConfig rateLimitConfig) {
        this.rateLimitConfig = rateLimitConfig;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long remainingTokens = rateLimitConfig.getRate();

        if (remainingTokens <= 0) {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse().bufferFactory()
                    .wrap("Too Many Requests".getBytes())));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}