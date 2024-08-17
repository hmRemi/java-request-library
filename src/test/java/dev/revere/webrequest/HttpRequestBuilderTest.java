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