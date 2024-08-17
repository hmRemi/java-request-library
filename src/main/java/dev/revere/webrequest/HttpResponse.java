package dev.revere.webrequest;

/**
 * @author Remi
 * @project java-request-library
 * @date 8/17/2024
 * <p>
 * Represents an HTTP response.
 *
 * @param statusCode the status code of the response
 * @param body the body of the response as a string
 */
public record HttpResponse(int statusCode, String body) {

    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusCode=" + statusCode +
                ", body='" + body + '\'' +
                '}';
    }
}