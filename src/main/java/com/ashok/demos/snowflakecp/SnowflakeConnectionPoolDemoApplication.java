package com.ashok.demos.snowflakecp;

import com.ashok.demos.snowflakecp.config.SnowflakeConfigProperties;
import com.ashok.demos.snowflakecp.config.SystemProxies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class, JdbcRepositoriesAutoConfiguration.class,
        DataSourceHealthContributorAutoConfiguration.class
})
@EnableCaching
@EnableFeignClients
@EnableConfigurationProperties({SnowflakeConfigProperties.class, SystemProxies.class})
public class SnowflakeConnectionPoolDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowflakeConnectionPoolDemoApplication.class, args);
    }

}
