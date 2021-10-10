package ru.shishkin.domain;

import java.util.Objects;

public class Post {
    private final long id;
    private final long date;
    private final String text;

    public Post(long id, long date, String text) {
        this.id = id;
        this.date = date;
        this.text = text.trim();
    }

    public long getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && date == post.date && Objects.equals(text, post.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, text);
    }
}
