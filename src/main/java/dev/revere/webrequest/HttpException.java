package dev.revere.webrequest;

import java.io.Serial;

/**
 * @author Remi
 * @project java-request-library
 * @date 8/17/2024
 * <p>
 * Custom exception class for handling HTTP-related errors.
 */
public class HttpException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int statusCode;

    /**
     * Constructs a new HttpException with the specified status code and detail message.
     *
     * @param statusCode the HTTP status code associated with the exception
     * @param message    the detail message
     */
    public HttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Constructs a new HttpException with the specified status code, detail message, and cause.
     *
     * @param statusCode the HTTP status code associated with the exception
     * @param message    the detail message
     * @param cause      the cause of the exception
     */
    public HttpException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    /**
     * Returns the HTTP status code associated with the exception.
     *
     * @return the HTTP status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns a string representation of the exception.
     *
     * @return a string representation of the exception
     */
    @Override
    public String toString() {
        return "HttpException{" +
                "statusCode=" + statusCode +
                ", message=" + getMessage() +
                ", cause=" + getCause() +
                '}';
    }
}