package ru.shishkin.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.shishkin.rule.HostReachableRule;

import static ru.shishkin.http.UrlReaderTest.HOST;

@HostReachableRule.HostReachable(HOST)
public class UrlReaderTest {
    final static String HOST = "https://vk.com:443";

    @Test
    public void read() {
        String result = new UrlReader()
                .readAsText(HOST);
        Assertions.assertTrue(result.length() > 0);
    }
}
