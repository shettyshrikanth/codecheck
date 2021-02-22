package com.hsbc.codecheck.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mongodb")
@Data
public class MongoDbProperties {
    private String host;
    private int port;
    private String databaseName;
}
