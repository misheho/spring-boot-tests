# Steps to Add a Simple REST API to spb-app

1. Open the existing controller file:
   - `spb-app/src/main/java/com/misheho/spb_app/HelloController.java`

2. Add a new REST endpoint:
   - Import `java.util.Map`.
   - Add a new `@GetMapping("/api/greeting")` method.
   - Return a simple JSON payload with `Map.of(...)`.

   Example:
   ```java
   @GetMapping("/api/greeting")
   public Map<String, String> greeting() {
       return Map.of(
           "message", "Hello from the Spring Boot REST API!",
           "status", "ok"
       );
   }
   ```

3. Keep the existing `/hello` endpoint intact.

4. Add or update a test file:
   - Edit `spb-app/src/test/java/com/misheho/spb_app/SpbAppApplicationTests.java`.
   - Add the annotations `@SpringBootTest` and `@AutoConfigureMockMvc` to the test class so Spring Boot will start an application context and configure MockMvc for HTTP testing.
   - Autowire a `MockMvc` field in the test class.
   - Add a new test method that performs `GET /api/greeting` and verifies:
     - the response status is `200 OK`
     - the content type is `application/json`
     - the JSON body contains `message` with the greeting text
     - the JSON body contains `status` with the value `ok`

   Example test class:
   ```java
   package com.misheho.spb_app;

   import org.junit.jupiter.api.Test;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.http.MediaType;
   import org.springframework.test.web.servlet.MockMvc;

   import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
   import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
   import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
   import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

   @SpringBootTest
   @AutoConfigureMockMvc
   class SpbAppApplicationTests {

       @Autowired
       private MockMvc mockMvc;

       @Test
       void contextLoads() {
       }

       @Test
       void greetingEndpointReturnsJson() throws Exception {
           mockMvc.perform(get("/api/greeting"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.message").value("Hello from the Spring Boot REST API!"))
               .andExpect(jsonPath("$.status").value("ok"));
       }
   }
   ```

5. Verify locally:
   - Run from the `spb-app` directory:
     ```bash
     ./mvnw -q test
     ```

## Result

The app should then expose:

- `GET /hello`
- `GET /api/greeting`

The new endpoint returns JSON like:

```json
{
  "message": "Hello from the Spring Boot REST API!",
  "status": "ok"
}
```
