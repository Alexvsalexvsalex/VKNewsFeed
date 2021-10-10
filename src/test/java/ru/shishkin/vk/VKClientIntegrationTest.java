package ru.shishkin.vk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.shishkin.config.ClientConfiguration;
import ru.shishkin.domain.PostsHolder;
import ru.shishkin.rule.HostReachableRule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author akirakozov
 */
@HostReachableRule.HostReachable(VKClientIntegrationTest.HOST)
public class VKClientIntegrationTest {
    public static final String HOST = "api.vk.com";

    @Test
    public void getPosts() throws IOException {
        VKClient client = new VKClient(new ClientConfiguration());
        LocalDateTime now = LocalDateTime.now();
        PostsHolder holder = client.getPosts("cat",
                now.minusHours(24).toEpochSecond(ZoneOffset.UTC),
                now.toEpochSecond(ZoneOffset.UTC));
        Assertions.assertTrue(holder.getCount() >= holder.getAvailablePosts().size());
        Assertions.assertTrue(holder.getCount() >= 0);
    }
}

