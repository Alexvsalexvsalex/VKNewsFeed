package ru.shishkin.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ClientConfiguration {
    private final String host;
    private final int port;
    private final String apiVersion;
    private final String serviceToken;

    public ClientConfiguration() throws IOException {
        try (FileReader reader = new FileReader("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(reader);
            host = properties.getProperty("host");
            port = Integer.parseInt(properties.getProperty("port"));
            apiVersion = properties.getProperty("api_version");
            serviceToken = properties.getProperty("service_token");
        }
    }

    ClientConfiguration(String host, int port, String apiVersion, String serviceToken) {
        this.host = host;
        this.port = port;
        this.apiVersion = apiVersion;
        this.serviceToken = serviceToken;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getServiceToken() {
        return serviceToken;
    }
}
