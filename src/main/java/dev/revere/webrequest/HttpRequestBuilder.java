package dev.revere.webrequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Remi
 * @project java-request-library
 * @date 8/17/2024
 * <p>
 * Builds an HTTP request.
 */
public class HttpRequestBuilder {
    private String url;
    private String method = "GET";
    private final Map<String, String> headers = new HashMap<>();
    private String body;

    /**
     * Sets the URL of the request.
     *
     * @param url the URL of the request
     * @return the HttpRequestBuilder
     */
    public HttpRequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * Sets the method of the request.
     *
     * @param method the method of the request
     * @return the HttpRequestBuilder
     */
    public HttpRequestBuilder method(String method) {
        this.method = method;
        return this;
    }

    /**
     * Adds a header to the request.
     *
     * @param key the key of the header
     * @param value the value of the header
     * @return the HttpRequestBuilder
     */
    public HttpRequestBuilder addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    /**
     * Sets the body of the request.
     *
     * @param body the body of the request
     * @return the HttpRequestBuilder
     */
    public HttpRequestBuilder body(String body) {
        this.body = body;
        return this;
    }

    /**
     * Builds the request.
     *
     * @return the HttpURLConnection
     * @throws IOException if an I/O error occurs
     */
    public HttpURLConnection build() throws IOException {
        URI uri = URI.create(url);
        URL urlObj = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod(method);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        if (body != null && !body.isEmpty()) {
            connection.setDoOutput(true);
            connection.getOutputStream().write(body.getBytes());
        }

        return connection;
    }
}