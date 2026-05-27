package com.starcritic.dam_proyectspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Expone un RestClient.Builder como bean para inyectarlo en los
 * servicios que consumen APIs externas (OMDb, RAWG). En Spring Boot 4 este
 * builder no se autoconfigura por defecto.
 * @author Jesús Santos Baquero
 */
@Configuration
public class RestClientConfig {

    /**
     * Crea el bean del builder de {@link RestClient} reutilizable por los servicios
     * que llaman a APIs externas (OMDb, RAWG).
     * @return el builder de {@link RestClient}.
     */
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}
