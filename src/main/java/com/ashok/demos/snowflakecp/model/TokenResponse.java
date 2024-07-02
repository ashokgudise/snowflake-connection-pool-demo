package com.ashok.demos.snowflakecp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Getter @Setter
public class TokenResponse {
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private Instant expiresAt;

    public void setExpiresIn(final long expiresIn){
        this.expiresIn = expiresIn;
        this.expiresAt = Instant.now().plusSeconds(expiresIn);
    }

    public boolean hasExpired(){
        return Instant.now().isAfter(expiresAt);
    }
}
