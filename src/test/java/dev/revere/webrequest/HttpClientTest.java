package dev.revere.webrequest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Remi
 * @project java-request-library
 * @date 8/17/2024
 */
class HttpClientTest {

    private static HttpClient client;

    @BeforeAll
    static void setUp() {
        client = HttpClient.getInstance();
    }

    @AfterAll
    static void tearDown() {
        if (client != null) {
            client.shutdown();
        }
    }

    @Test
    void testExecuteSync() throws Exception {
        HttpURLConnection connection = new HttpRequestBuilder()
                .url("https://jsonplaceholder.typicode.com/posts/1")
                .method("GET")
                .build();

        HttpResponse response = client.execute(connection);

        assertNotNull(response);
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("userId"));
    }

    @Test
    void testExecuteAsync() throws Exception {
        HttpURLConnection connection = new HttpRequestBuilder()
                .url("https://jsonplaceholder.typicode.com/posts/1")
                .method("GET")
                .build();

        CompletableFuture<HttpResponse> futureResponse = client.executeAsync(connection);

        HttpResponse response = futureResponse.join();

        assertNotNull(response);
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("userId"));
    }
}