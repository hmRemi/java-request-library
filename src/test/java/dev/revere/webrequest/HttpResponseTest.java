package dev.revere.webrequest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Remi
 * @project java-request-library
 * @date 8/17/2024
 */
class HttpResponseTest {

    @Test
    void testHttpResponseCreation() {
        HttpResponse response = new HttpResponse(200, "{\"message\":\"success\"}");

        assertEquals(200, response.statusCode());
        assertEquals("{\"message\":\"success\"}", response.body());
    }

    @Test
    void testHttpResponseToString() {
        HttpResponse response = new HttpResponse(200, "{\"message\":\"success\"}");

        String expected = "HttpResponse{statusCode=200, body='{\"message\":\"success\"}'}";
        assertEquals(expected, response.toString());
    }
}
