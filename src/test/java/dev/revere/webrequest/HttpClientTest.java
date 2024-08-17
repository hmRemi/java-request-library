/*
 * MIT License
 *
 * Copyright (c) 2024 Revere Development
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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