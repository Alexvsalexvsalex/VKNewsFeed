package ru.shishkin.http;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;


public class UrlReaderWIthStubServerTest {
    private static final int PORT = 32453;
    private final UrlReader urlReader = new UrlReader();

    @Test
    public void readAsText() {
        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/ping"))
                    .then(stringContent("pong"));

            String result = urlReader.readAsText("http://localhost:" + PORT + "/ping");

            Assertions.assertEquals("pong\n", result);
        });
    }

    @Test
    public void readAsTextWithNotFoundError() {
        Assertions.assertThrows(UncheckedIOException.class, () -> {
            withStubServer(PORT, s -> {
                whenHttp(s)
                        .match(method(Method.GET), startsWithUri("/ping"))
                        .then(status(HttpStatus.NOT_FOUND_404));

                urlReader.readAsText("http://localhost:" + PORT + "/ping");
            });
        });
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
