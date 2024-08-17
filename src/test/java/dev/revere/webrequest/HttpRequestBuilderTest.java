package dev.revere.webrequest;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Remi
 * @project java-request-library
 * @date 8/17/2024
 */
class HttpRequestBuilderTest {

    @Test
    void testBuildGetRequest() throws Exception {
        HttpRequestBuilder builder = new HttpRequestBuilder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .method("GET");

        HttpURLConnection connection = builder.build();

        assertNotNull(connection);
        assertEquals("GET", connection.getRequestMethod());
        assertEquals("https://jsonplaceholder.typicode.com/posts", connection.getURL().toString());
    }

    @Test
    void testBuildPostRequestWithBody() throws Exception {
        String jsonBody = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";

        HttpRequestBuilder builder = new HttpRequestBuilder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .method("POST")
                .addHeader("Content-Type", "application/json")
                .body(jsonBody);

        HttpURLConnection connection = builder.build();

        assertNotNull(connection);
        assertEquals("POST", connection.getRequestMethod());
        assertEquals("https://jsonplaceholder.typicode.com/posts", connection.getURL().toString());
        assertEquals("application/json", connection.getRequestProperty("Content-Type"));
    }
}