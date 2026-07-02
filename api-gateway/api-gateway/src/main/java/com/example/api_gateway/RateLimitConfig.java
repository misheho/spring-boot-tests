package com.example.api_gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom.rate-limit")
public class RateLimitConfig {
    private long rate = 10;
    private long burstCapacity = 20;
    private int replenishmentRate = 100;

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public long getBurstCapacity() {
        return burstCapacity;
    }

    public void setBurstCapacity(long burstCapacity) {
        this.burstCapacity = burstCapacity;
    }

    public int getReplenishmentRate() {
        return replenishmentRate;
    }

    public void setReplenishmentRate(int replenishmentRate) {
        this.replenishmentRate = replenishmentRate;
    }
}