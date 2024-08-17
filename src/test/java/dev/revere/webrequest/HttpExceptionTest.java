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