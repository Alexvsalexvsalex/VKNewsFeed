package ru.shishkin.vk;

import ru.shishkin.config.ClientConfiguration;
import ru.shishkin.domain.PostsHolder;
import ru.shishkin.http.UrlReader;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class VKClient {
    private final String host;
    private final int port;
    private final String apiVersion;
    private final String serviceToken;
    private final VKResponseParser parser;
    private final UrlReader reader;

    public VKClient(ClientConfiguration configuration) {
        this.host = configuration.getHost();
        this.port = configuration.getPort();
        this.apiVersion = configuration.getApiVersion();
        this.serviceToken = configuration.getServiceToken();
        this.parser = new VKResponseParser();
        this.reader = new UrlReader();
    }

    public PostsHolder getPosts(String tag, long from, long to) {
        String response = reader.readAsText(createUrl(tag, from, to));
        return parser.parseResponse(response);
    }

    private String createUrl(String tag, long from, long to) {
        return String.format("%s:%d/method/newsfeed.search?q=%s&start_time=%d&end_time=%d&access_token=%s&v=%s",
                host, port, URLEncoder.encode(tag, StandardCharsets.UTF_8), from, to, serviceToken, apiVersion);
    }
}
