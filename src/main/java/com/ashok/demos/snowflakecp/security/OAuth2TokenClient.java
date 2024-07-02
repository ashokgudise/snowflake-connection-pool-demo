package com.ashok.demos.snowflakecp.security;

import com.ashok.demos.snowflakecp.config.SnowflakeConfigProperties;
import com.ashok.demos.snowflakecp.model.TokenResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="oauth2clientService", url="{app.snowflake.access-token-uri}")
public interface OAuth2TokenClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Cacheable("CACHE_TOKEN")
    TokenResponse getAccessToken(@RequestBody SnowflakeConfigProperties.OauthConfig oauthConfig);
}
