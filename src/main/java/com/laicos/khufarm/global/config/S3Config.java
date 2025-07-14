package com.laicos.khufarm.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${cloud.ncp.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.ncp.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.ncp.region}")
    private String region;

    @Value("${cloud.ncp.s3.endpoint}")
    private String endpoint;

    @Bean
    public AmazonS3 amazonS3Client() {
        // 가이드의 코드와 동일하게 AccessKey, SecretKey로 인증 정보를 생성합니다.[1]
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        // Endpoint와 region을 설정하여 S3 클라이언트를 빌드합니다.[1]
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}