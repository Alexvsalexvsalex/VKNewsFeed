package ru.shishkin.vk;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.shishkin.domain.Post;
import ru.shishkin.domain.PostsHolder;

import java.util.ArrayList;
import java.util.List;

public class VKResponseParser {
    public PostsHolder parseResponse(String response) {
        JsonObject root = JsonParser.parseString(response).getAsJsonObject().getAsJsonObject("response");
        long totalCount = root.get("total_count").getAsLong();
        JsonArray rawPosts = root.getAsJsonArray("items");
        List<Post> posts = new ArrayList<>(rawPosts.size());
        for (JsonElement e : rawPosts) {
            JsonObject d = (JsonObject) e;
            posts.add(new Post(
                    d.get("id").getAsLong(),
                    d.get("date").getAsLong(),
                    d.get("text").getAsString()));
        }

        return new PostsHolder(posts, totalCount);
    }

}
