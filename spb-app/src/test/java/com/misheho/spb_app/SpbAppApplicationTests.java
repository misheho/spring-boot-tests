package com.misheho.spb_app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
			.andExpect(jsonPath("$.message").value("Hello, Spring Boot user from the Spring Boot REST API!"))
			.andExpect(jsonPath("$.status").value("ok"));
	}

	@Test
	void greetingEndpointUsesRequestorName() throws Exception {
		mockMvc.perform(get("/api/greeting").param("name", "Alice"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").value("Hello, Alice from the Spring Boot REST API!"))
			.andExpect(jsonPath("$.status").value("ok"));
	}

	@Test
	void greetingPostEndpointWithName() throws Exception {
		mockMvc.perform(post("/api/greeting")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\": \"Bob\"}"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").value("Hello, Bob from the Spring Boot REST API!"))
			.andExpect(jsonPath("$.status").value("ok"));
	}

	@Test
	void greetingPostEndpointWithoutName() throws Exception {
		mockMvc.perform(post("/api/greeting")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").value("Hello, Spring Boot user from the Spring Boot REST API!"))
			.andExpect(jsonPath("$.status").value("ok"));
	}

}
