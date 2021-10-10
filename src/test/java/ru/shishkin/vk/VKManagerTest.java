package ru.shishkin.vk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * @author akirakozov
 */
public class VKManagerTest {

    private VKManager vkManager;

    @Mock
    private VKClient client;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vkManager = new VKManager(client);

        when(client.getPosts(anyString(), anyLong(), anyLong()))
                .thenAnswer(query ->
                        new Utils().getTestPosts(
                                query.getArgumentAt(0, String.class),
                                query.getArgumentAt(1, Long.class),
                                query.getArgumentAt(2, Long.class)));
    }

    @Test
    public void getPostsStatistics() {
        List<Long> result1 = vkManager.getPostsStatistics("#tag", 4);
        Assertions.assertEquals(List.of(0L, 1L, 1L, 0L), result1);

        List<Long> result2 = vkManager.getPostsStatistics("#2tag", 5);
        Assertions.assertEquals(List.of(0L, 1L, 0L, 0L, 0L), result2);
    }
}
