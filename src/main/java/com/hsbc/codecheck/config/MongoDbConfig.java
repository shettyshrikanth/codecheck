package com.hsbc.codecheck.config;

import com.hsbc.codecheck.config.properties.MongoDbProperties;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;

import static java.lang.String.format;

@Configuration
public class MongoDbConfig extends AbstractMongoClientConfiguration {

    @Autowired
    private MongoDbProperties mongoDbProperties;

    @Override
    protected String getDatabaseName() {
        return mongoDbProperties.getDatabaseName();
    }

    @Override
    public MongoClient mongoClient() {

        var url = format("mongodb://%s:%d/%s", mongoDbProperties.getHost(), mongoDbProperties.getPort(), mongoDbProperties.getDatabaseName());
        var connectionString = new ConnectionString(url);
        var mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.hsbc.codecheck.entity");
    }
}
