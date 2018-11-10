package io.hfgbarrigas.oauth2.oauth2clientwebflux.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("iam.reactive.client")
public class WebClientProperties {
    private String host;
    private String scheme;
    private String basePath;
    private int port;
    private int writeTimeout = 0;
    private int readTimeout = 0;
    private int readWriteTimeout = 0;
    private int maxConnections;
    private String defaultClientRegistrationName = "iam";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getReadWriteTimeout() {
        return readWriteTimeout;
    }

    public void setReadWriteTimeout(int readWriteTimeout) {
        this.readWriteTimeout = readWriteTimeout;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getDefaultClientRegistrationName() {
        return defaultClientRegistrationName;
    }

    public void setDefaultClientRegistrationName(String defaultClientRegistrationName) {
        this.defaultClientRegistrationName = defaultClientRegistrationName;
    }
}
