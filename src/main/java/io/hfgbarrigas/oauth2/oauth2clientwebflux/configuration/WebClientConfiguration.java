package io.hfgbarrigas.oauth2.oauth2clientwebflux.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hfgbarrigas.oauth2.oauth2clientwebflux.properties.WebClientProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(WebClientProperties.class)
public class WebClientConfiguration {

    @Bean("iam-webclient")
    public WebClient webClient(ObjectMapper objectMapper,
                               WebClientProperties webClientProperties,
                               ReactiveClientRegistrationRepository clientRegistrationRepo,
                               ServerOAuth2AuthorizedClientRepository authorizedClientRepo) {

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepo,
                authorizedClientRepo);

        oauth.setDefaultClientRegistrationId(webClientProperties.getDefaultClientRegistrationName());
        oauth.setDefaultOAuth2AuthorizedClient(true);

        return WebClient
                .builder()
                .exchangeStrategies(ExchangeStrategies
                        .builder()
                        .codecs(clientDefaultCodecsConfigurer -> {
                            clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                            clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                        }).build())
                .clientConnector(clientHttpConnector(webClientProperties))
                .filter(oauth)
                .build();
    }

    private ClientHttpConnector clientHttpConnector(WebClientProperties webClientProperties) {
        return new ReactorClientHttpConnector(HttpClient.create()
                .compress(true)
                .keepAlive(true)
                .tcpConfiguration(tcpClient -> tcpClient
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
                        .doOnConnected(connection -> connection
                                .addHandlerFirst("idleStateHandler", new IdleStateHandler(webClientProperties.getReadTimeout(),
                                        webClientProperties.getWriteTimeout(), webClientProperties.getReadWriteTimeout(), TimeUnit.MILLISECONDS))
                                .addHandler("customIdleStateHandler", new CustomIdleStateHandler())))

        );
    }
}
