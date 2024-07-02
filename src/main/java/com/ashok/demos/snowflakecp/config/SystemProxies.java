package com.ashok.demos.snowflakecp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.proxy")
public class SystemProxies {
    private String host;
    private String port;
}
