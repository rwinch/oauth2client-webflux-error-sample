package io.hfgbarrigas.oauth2.oauth2clientwebflux.configuration;

import io.hfgbarrigas.oauth2.oauth2clientwebflux.properties.ResourceServerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableConfigurationProperties(ResourceServerProperties.class)
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain defaultHoldSpringSecurityFilterChain(ServerHttpSecurity http,
                                                                       ResourceServerProperties resourceServerProperties,
                                                                       NimbusReactiveJwtDecoder nimbusReactiveJwtDecoder) {
        http
                .authorizeExchange()
                .pathMatchers(resourceServerProperties.getPublicEndpoints().toArray(new String[]{}))
                .permitAll()
                .anyExchange().authenticated()
                .and()
                .csrf()
                .disable()
                .oauth2ResourceServer()
                .jwt()
                .jwtDecoder(nimbusReactiveJwtDecoder);

        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public NimbusReactiveJwtDecoder nimbusReactiveJwtDecoder(ResourceServerProperties resourceServerProperties) {
        return new NimbusReactiveJwtDecoder(resourceServerProperties.getDefaultVerifierKeyUrl());
    }
}
