# Java Request Library

A lightweight Java HTTP client library for making synchronous and asynchronous HTTP requests. This library supports custom headers, request bodies, and timeouts. It also includes error handling for HTTP-related issues.

## Features

- **Synchronous and Asynchronous Requests**: Execute HTTP requests both synchronously and asynchronously.
- **Customizable Requests**: Build HTTP requests with custom headers, methods, and bodies.
- **Error Handling**: Robust error handling with detailed exceptions.
- **Thread Management**: Uses a cached thread pool for asynchronous operations.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
  - [HttpClient](#httpclient)
  - [HttpRequestBuilder](#httprequestbuilder)
  - [HttpResponse](#httpresponse)
  - [HttpException](#httpexception)
- [License](#license)
- [Contributing](#contributing)
- [Author](#author)

## Installation

1. Clone the repository:
   `git clone https://github.com/hmRemi/java-request-library.git`

2. Navigate to the project directory:
   `cd java-request-library`

3. Build the project with Maven:
   `mvn clean install`

## Usage

### HttpClient

`HttpClient` provides methods to execute HTTP requests both synchronously and asynchronously.

#### Example

```java
import dev.revere.webrequest.HttpClient;
import dev.revere.webrequest.HttpRequestBuilder;
import dev.revere.webrequest.HttpResponse;

import java.net.HttpURLConnection;

public class Example {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.getInstance();

        HttpURLConnection connection = new HttpRequestBuilder()
                .url("https://jsonplaceholder.typicode.com/posts/1")
                .method("GET")
                .build();

        HttpResponse response = client.execute(connection);
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

        client.shutdown();
    }
}
```

### HttpRequestBuilder

`HttpRequestBuilder` is used to construct HTTP requests with various options like URL, method, headers, and body.

#### Example

```java
import dev.revere.webrequest.HttpRequestBuilder;
import java.net.HttpURLConnection;

public class Example {
    public static void main(String[] args) throws Exception {
        HttpRequestBuilder builder = new HttpRequestBuilder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .method("POST")
                .addHeader("Content-Type", "application/json")
                .body("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}");

        HttpURLConnection connection = builder.build();
        // Use the connection with HttpClient
    }
}
```

### HttpResponse

`HttpResponse` represents the response from an HTTP request, containing the status code and response body.

#### Example

```java
import dev.revere.webrequest.HttpResponse;

public class Example {
    public static void main(String[] args) {
        HttpResponse response = new HttpResponse(200, "{\"message\":\"success\"}");
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Body: " + response.body());
    }
}
```

### HttpException

`HttpException` is a custom exception class used for handling HTTP-related errors.

#### Example

```java
import dev.revere.webrequest.HttpException;

public class Example {
    public static void main(String[] args) {
        try {
            throw new HttpException(404, "Not Found");
        } catch (HttpException e) {
            System.out.println("Error: " + e.getStatusCode() + " " + e.getMessage());
        }
    }
}
```


## License

For an open-source license that requires attribution to Revere Development, the [MIT License](https://opensource.org/licenses/MIT) is a suitable choice. Here is the license text with the necessary attribution:

```text
MIT License

Copyright (c) 2024 Revere Development

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Contributing

We welcome contributions from the community! If you'd like to contribute to the project, please follow these steps:

1. **Fork the Project:** Start by forking the project to your own GitHub account using the "Fork" button at the top right of this repository.
2. **Create a New Branch:** Create a new branch in your forked repository. This branch will be dedicated to your feature, enhancement, or bug fix.
3. **Make Changes:** Implement your desired changes, whether it's a new feature, improvement, or fixing a bug. Please ensure your code adheres to the project's coding standards.
4. **Commit Your Changes:** Commit your changes with clear and concise commit messages that describe the purpose of each change.
5. **Push to Your Fork:** Push your changes to your forked repository on GitHub.
6. **Create a Pull Request:** Once you've pushed your changes to your fork, go to the original repository and create a pull request. Provide a detailed description of your changes and why they are valuable.

## Author

The Java Request Library is developed and maintained by Revere Development. For any inquiries or to get in touch with the team, you can reach us at:

- **Website**: [revere.dev](https://www.revere.dev)
- **Email**: [support@revere.dev](mailto:support@revere.dev)

Thank you for using and contributing to the Java Request Library!
