package ru.shishkin.vk;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VKManager {
    private final VKClient client;

    public VKManager(VKClient client) {
        this.client = client;
    }

    public List<Long> getPostsStatistics(String tag, int lastHoursCnt) {
        LocalDateTime now = LocalDateTime.now();
        return IntStream.iterate(lastHoursCnt - 1, hour -> hour >= 0, hour -> hour - 1)
                .mapToLong(hour ->
                        client.getPosts(tag,
                            now.minusHours(hour + 1).toEpochSecond(ZoneOffset.UTC),
                            now.minusHours(hour).toEpochSecond(ZoneOffset.UTC)).getCount()
                ).boxed().collect(Collectors.toList());
    }
}
