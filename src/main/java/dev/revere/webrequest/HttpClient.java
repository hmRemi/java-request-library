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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Remi
 * @project java-request-library
 * @date 8/17/2024
 * <p>
 * Represents an HTTP client.
 */
public class HttpClient {

    private static class Holder {
        private static final HttpClient INSTANCE = new HttpClient();
    }

    private final ExecutorService executorService;

    /**
     * Private constructor to initialize the HttpClient with a cached thread pool.
     */
    private HttpClient() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public static HttpClient getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Executes an HTTP request synchronously.
     *
     * @param connection the HttpURLConnection to execute
     * @return the HttpResponse of the request
     * @throws HttpException if the request fails or an I/O error occurs
     */
    public HttpResponse execute(HttpURLConnection connection) throws HttpException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getResponseStream(connection)))) {
            int responseCode = connection.getResponseCode();
            String responseBody = readResponseBody(reader);

            if (responseCode >= 400) {
                throw new HttpException(responseCode, String.format("HTTP request failed with status code: %d", responseCode));
            }

            return new HttpResponse(responseCode, responseBody);
        } catch (IOException e) {
            throw new HttpException(500, "I/O error occurred while processing the request", e);
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Executes an HTTP request asynchronously.
     *
     * @param connection the HttpURLConnection to execute
     * @return a CompletableFuture that completes with the HttpResponse
     */
    public CompletableFuture<HttpResponse> executeAsync(HttpURLConnection connection) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return execute(connection);
            } catch (HttpException e) {
                throw new CompletionException(e);
            }
        }, executorService).orTimeout(60, TimeUnit.SECONDS);
    }

    /**
     * Shuts down the HttpClient and its ExecutorService.
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Gets the appropriate InputStream based on the response code.
     *
     * @param connection the HttpURLConnection
     * @return the InputStream to read the response
     * @throws IOException if an I/O error occurs
     */
    private InputStream getResponseStream(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        return (responseCode >= 200 && responseCode < 300)
                ? connection.getInputStream()
                : connection.getErrorStream();
    }

    /**
     * Reads the response body from the BufferedReader.
     *
     * @param reader the BufferedReader to read from
     * @return the response body as a string
     * @throws IOException if an I/O error occurs
     */
    private String readResponseBody(BufferedReader reader) throws IOException {
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }
}