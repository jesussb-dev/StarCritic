package com.starcritic.dam_proyectspringboot.config;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

/**
 * Expone el cliente y el presigner de Cloudflare R2 (API compatible con S3)
 * como beans. Las credenciales y el endpoint se leen de application.properties
 * ({@code cloudflare.*}), de modo que el secreto vive solo en el servidor.
 * @author Jesús Santos Baquero
 */
@Configuration
public class CloudflareR2Config {

    @Value("${cloudflare.endpoint}")
    private String endpoint;
    @Value("${cloudflare.access-key}")
    private String accessKey;
    @Value("${cloudflare.secret-key}")
    private String secretKey;

    private StaticCredentialsProvider credenciales() {
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey));
    }

    /**
     * Cliente S3 para subir, descargar y eliminar objetos en R2.
     * @return el {@link S3Client} configurado contra el endpoint de Cloudflare.
     */
    @Bean
    public S3Client s3Client() {
        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .chunkedEncodingEnabled(false)
                .build();

        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(credenciales())
                .region(Region.of("auto"))
                .serviceConfiguration(serviceConfiguration)
                .build();
    }

    /**
     * Presigner S3 para generar URLs temporales de lectura.
     * @return el {@link S3Presigner} configurado contra el endpoint de Cloudflare.
     */
    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(credenciales())
                .region(Region.of("auto"))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }
}
