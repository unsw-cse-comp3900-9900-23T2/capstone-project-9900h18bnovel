package com.example.novel_backend.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Cross-domain configuration
 *
 * @author :Contanstin
 * @create 2023/6/23 16:00
 */
@Configuration
@EnableConfigurationProperties(CorsProperties.class)
@RequiredArgsConstructor
public class CorsConfig {

    private final CorsProperties corsProperties;

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        // Allowed fields, do not write *, otherwise the cookie will not work
        for (String allowOrigin : corsProperties.getAllowOrigins()) {
            config.addAllowedOrigin(allowOrigin);
        }
        // Permissible headers
        config.addAllowedHeader("*");
        // Allowed request methods
        config.addAllowedMethod("*");
        // Whether to allow cookie information to be carried
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        // Add mapped paths to intercept all requests
        configurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configurationSource);
    }
}
