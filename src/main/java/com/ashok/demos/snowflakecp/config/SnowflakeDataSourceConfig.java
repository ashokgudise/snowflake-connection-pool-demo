package com.ashok.demos.snowflakecp.config;

import com.ashok.demos.snowflakecp.model.TokenResponse;
import com.ashok.demos.snowflakecp.security.OAuth2TokenClient;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class SnowflakeDataSourceConfig {

    private SnowflakeConfigProperties configProps;
    private OAuth2TokenClient oAuth2TokenClient;
    private SystemProxies proxies;

    SnowflakeDataSourceConfig(final SnowflakeConfigProperties configProps, final OAuth2TokenClient tokenClient,
                              final SystemProxies proxies) {
        this.configProps = configProps;
        this.oAuth2TokenClient = tokenClient;
        this.proxies = proxies;
    }

    @Bean
    @Qualifier("snowflakeDatasource")
    public DataSource datasource(){

        Properties props = new Properties();
        props.setProperty("authenticator", configProps.getAuthentication());
        props.setProperty("role", configProps.getRole());
        props.setProperty("db", configProps.getDatabase());
        props.setProperty("schema", configProps.getDatabase());
        props.setProperty("warehouse", configProps.getDatabase());
        props.setProperty("useProxy", "true");
        props.setProperty("proxyHost", proxies.getHost());
        props.setProperty("proxyPort", proxies.getPort());

        CustomizedSnowflakeDataSource snowflakeDS = new CustomizedSnowflakeDataSource(configProps.getUrl(), props);

        HikariDataSource hikariDS = new HikariDataSource();
        hikariDS.setDataSource(snowflakeDS);

        SnowflakeConfigProperties.Hikari hikari = configProps.getHikari();

        //Customize if you want to override any HikariConfig with hikari props
        hikariDS.setPoolName(hikari.getPoolName());

        return hikariDS;
    }

    private class CustomizedSnowflakeDataSource extends SimpleDriverDataSource {
        public CustomizedSnowflakeDataSource(final String url, final Properties props){
            super(new net.snowflake.client.jdbc.SnowflakeDriver(), url ,props);
        }

        @Override
        protected Connection getConnectionFromDriver(final Properties props) throws SQLException {
            TokenResponse response = oAuth2TokenClient.getAccessToken(configProps.getOauthConfig());
            props.setProperty("token", response.getAccessToken());
            return super.getConnectionFromDriver(props);
        }
    }

    @Bean
    @Qualifier("snowflakeJdbcTemplate")
    public JdbcTemplate snowflakeJdbcTemplate(@Qualifier("snowflakeDatasource") final DataSource dataSource){
        JdbcTemplate snowflakeJdbcTemplate = new JdbcTemplate();
        snowflakeJdbcTemplate.setDataSource(dataSource);

        return snowflakeJdbcTemplate;
    }
}
