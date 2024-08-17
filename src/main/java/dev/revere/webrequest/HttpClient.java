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