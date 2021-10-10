package ru.shishkin.domain;

import java.util.List;

public class PostsHolder {
    private final List<Post> availablePosts;
    private final long count;

    public PostsHolder(List<Post> availablePosts, long count) {
        this.availablePosts = availablePosts;
        this.count = count;
    }

    public List<Post> getAvailablePosts() {
        return availablePosts;
    }

    public long getCount() {
        return count;
    }
}
