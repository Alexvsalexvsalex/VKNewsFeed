package ru.shishkin.vk;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shishkin.config.ClientConfiguration;
import ru.shishkin.config.TestConfiguration;
import ru.shishkin.domain.PostsHolder;
import ru.shishkin.http.UrlReader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.*;


public class VKClientWIthStubServerTest {
    private final ClientConfiguration configuration = TestConfiguration.configuration;
    private final VKClient client = new VKClient(configuration);

    private void makeQuery(String tag, long from, long to) {
        withStubServer(configuration.getPort(), s -> {
            PostsHolder answer = new Utils().getTestPosts(tag, from, to);
            whenHttp(s).match(method(Method.GET),
                            startsWithUri("/method/newsfeed.search"),
                            parameter("start_time", Long.toString(from)),
                            parameter("end_time", Long.toString(to)),
                            parameter("q", tag),
                            parameter("access_token", configuration.getServiceToken()),
                            parameter("v", configuration.getApiVersion()))
                    .then(stringContent(
                            "{\n" +
                            "   \"response\":{\n" +
                            "      \"items\":[],\n" +
                            "      \"count\":" + answer.getCount() + ",\n" +
                            "      \"total_count\":" + answer.getCount() + "\n" +
                            "   }\n" +
                            "}"));
            PostsHolder holder = client.getPosts(tag, from, to);

            Assertions.assertEquals(holder.getCount(), answer.getCount());
        });
    }

    @Test
    public void getPosts() {
        LocalDateTime now = LocalDateTime.now();
        long from = now.minusHours(24).toEpochSecond(ZoneOffset.UTC);
        long to = now.toEpochSecond(ZoneOffset.UTC);
        makeQuery("#cat", from, to);
        makeQuery("#itmo", from, to);
        makeQuery("#tag", from, to);
    }

    private void withStubServer(int port, Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(port).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
