package com.ashok.demos.snowflakecp.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Getter @Setter
@ConfigurationProperties(prefix = "app.snowflake")
public class SnowflakeConfigProperties {

    private String accessTokenUri;
    private String authentication;
    private String role;
    private String database;
    private String schema;
    private String warehouse;
    private String url;
    private String query;

    private final OauthConfig oauthConfig = new OauthConfig();
    private final Hikari hikari = new Hikari();

    @Data
    public static class OauthConfig{
        private String clientId;
        private String grantType;
        private String scope;
    }

    @Data
    public static class Hikari{
        private String connectionTimeout;
        private String idleTimeout;
        private String maxLifetime;
        private String maximumPoolSize;
        private String poolName;
    }
}
