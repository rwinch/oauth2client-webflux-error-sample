package io.hfgbarrigas.oauth2.oauth2clientwebflux.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

@ConfigurationProperties("hold.security")
public class ResourceServerProperties {

    private String defaultVerifierKeyUrl = "https://api-dev.hold.co/as/oauth/verifier-jwk";

    private List<String> publicEndpoints = Arrays.asList("/actuator/**", "/test/**", "/openapi.yaml");

    public String getDefaultVerifierKeyUrl() {
        return defaultVerifierKeyUrl;
    }

    public void setDefaultVerifierKeyUrl(String defaultVerifierKeyUrl) {
        this.defaultVerifierKeyUrl = defaultVerifierKeyUrl;
    }

    public List<String> getPublicEndpoints() {
        return publicEndpoints;
    }

    public void setPublicEndpoints(List<String> publicEndpoints) {
        this.publicEndpoints = publicEndpoints;
    }
}
