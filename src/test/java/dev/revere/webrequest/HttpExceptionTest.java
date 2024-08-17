package dev.revere.webrequest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Remi
 * @project java-request-library
 * @date 8/17/2024
 */
public class HttpExceptionTest {

    @Test
    public void testHttpExceptionWithMessage() {
        HttpException exception = new HttpException(404, "Not Found");
        assertEquals(404, exception.getStatusCode());
        assertEquals("Not Found", exception.getMessage());
    }

    @Test
    public void testHttpExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("Root Cause");
        HttpException exception = new HttpException(500, "Internal Server Error", cause);
        assertEquals(500, exception.getStatusCode());
        assertEquals("Internal Server Error", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testToString() {
        HttpException exception = new HttpException(400, "Bad Request");
        String expectedString = "HttpException{statusCode=400, message=Bad Request, cause=null}";
        assertEquals(expectedString, exception.toString());
    }
}