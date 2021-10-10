package ru.shishkin.vk;

import ru.shishkin.domain.Post;
import ru.shishkin.domain.PostsHolder;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private List<Post> getAllPosts() {
        LocalDateTime now = LocalDateTime.now().minusMinutes(20);
        return List.of(
                new Post(1, now.toEpochSecond(ZoneOffset.UTC), ""),
                new Post(2, now.minusHours(1).toEpochSecond(ZoneOffset.UTC), " #tag"),
                new Post(3, now.minusHours(3).toEpochSecond(ZoneOffset.UTC), " #2tag"),
                new Post(4, now.toEpochSecond(ZoneOffset.UTC), " #cat"),
                new Post(5, now.minusHours(2).toEpochSecond(ZoneOffset.UTC), "#tag"),
                new Post(6, now.minusHours(1).toEpochSecond(ZoneOffset.UTC), "#holiday#itmo"),
                new Post(7, now.toEpochSecond(ZoneOffset.UTC), "2tag")
        );
    }

    PostsHolder getTestPosts(String tag, long from, long to) {
        List<Post> posts = getAllPosts().stream()
                .filter(p -> p.getText().contains(tag) && from <= p.getDate() && p.getDate() < to)
                .collect(Collectors.toList());
        return new PostsHolder(posts, posts.size());
    }
}
