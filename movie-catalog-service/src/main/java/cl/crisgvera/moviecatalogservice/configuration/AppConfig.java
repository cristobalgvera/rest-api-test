package cl.crisgvera.moviecatalogservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        // This object brings possibility to make a rest call to another service
        return new RestTemplate();
    }

    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }
}
