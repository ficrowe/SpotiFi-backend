package com.ficrowe.smart.home.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class AwsConfiguration {

    @Bean
    public DynamoDbClient dynamoDbClient(@Autowired Region region, @Autowired ProfileCredentialsProvider credentialsProvider) {
        return DynamoDbClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(region)
                .build();
    }

    @Bean
    public Region region() {
        return Region.AP_SOUTHEAST_2;
    }

    @Bean
    public ProfileCredentialsProvider credentialsProvider() {
        return ProfileCredentialsProvider.create();
    }
}
