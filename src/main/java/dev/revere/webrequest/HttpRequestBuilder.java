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

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Remi
 * @project java-request-library
 * @date 8/17/2024
 * <p>
 * Builds an HTTP request with the specified URL, method, headers, body, and timeouts.
 */
public class HttpRequestBuilder {
    private URI uri;
    private String method = "GET";
    private final Map<String, String> headers = new HashMap<>();
    private String body;
    private int connectTimeout = 10000;
    private int readTimeout = 10000;

    /**
     * Sets the URL of the request.
     *
     * @param url the URL of the request
     * @return the HttpRequestBuilder instance
     * @throws IllegalArgumentException if the URL is invalid
     */
    public HttpRequestBuilder url(String url) {
        try {
            URI uri = new URI(url);
            if (!"http".equals(uri.getScheme()) && !"https".equals(uri.getScheme())) {
                throw new IllegalArgumentException("URL must start with http or https: " + url);
            }
            this.uri = uri;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL: " + url, e);
        }
        return this;
    }

    /**
     * Sets the method of the request.
     *
     * @param method the HTTP method of the request (e.g., GET, POST, PUT, DELETE)
     * @return the HttpRequestBuilder instance
     * @throws IllegalArgumentException if the method is invalid
     */
    public HttpRequestBuilder method(String method) {
        if (!isValidHttpMethod(method)) {
            throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        this.method = method.toUpperCase();
        return this;
    }

    /**
     * Adds a header to the request.
     *
     * @param key the key of the header
     * @param value the value of the header
     * @return the HttpRequestBuilder instance
     */
    public HttpRequestBuilder addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    /**
     * Sets the body of the request.
     *
     * @param body the body of the request
     * @return the HttpRequestBuilder instance
     */
    public HttpRequestBuilder body(String body) {
        this.body = body;
        return this;
    }

    /**
     * Sets the connection timeout in milliseconds.
     *
     * @param timeout the connection timeout in milliseconds
     * @return the HttpRequestBuilder instance
     */
    public HttpRequestBuilder connectTimeout(int timeout) {
        this.connectTimeout = timeout;
        return this;
    }

    /**
     * Sets the read timeout in milliseconds.
     *
     * @param timeout the read timeout in milliseconds
     * @return the HttpRequestBuilder instance
     */
    public HttpRequestBuilder readTimeout(int timeout) {
        this.readTimeout = timeout;
        return this;
    }

    /**
     * Builds the request.
     *
     * @return the HttpURLConnection representing the request
     * @throws IOException if an I/O error occurs while building the request
     */
    public HttpURLConnection build() throws IOException {
        if (uri == null) {
            throw new IllegalStateException("URL must be set before building the request.");
        }

        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);

        headers.forEach(connection::setRequestProperty);

        setRequestBody(connection);

        return connection;
    }

    /**
     * Sets the request body if it is present.
     *
     * @param connection the HttpURLConnection to set the body for
     * @throws IOException if an I/O error occurs
     */
    private void setRequestBody(HttpURLConnection connection) throws IOException {
        if (body != null && !body.isEmpty()) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(body.getBytes());
            }
        }
    }

    /**
     * Validates the HTTP method.
     *
     * @param method the HTTP method to validate
     * @return true if the method is valid, false otherwise
     */
    private boolean isValidHttpMethod(String method) {
        Set<String> validMethods = Set.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH");
        return validMethods.contains(method.toUpperCase());
    }
}