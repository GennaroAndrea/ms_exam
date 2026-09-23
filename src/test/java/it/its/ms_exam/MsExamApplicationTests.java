package it.its.ms_exam;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.jayway.jsonpath.JsonPath;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
class MsExamApplicationTests {

	@Container
	@ServiceConnection
	static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:17-alpine");

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void saveAndRetrieveItem() throws Exception {

		String body = "item_name";

		MvcResult postResult = mockMvc.perform(post("/api/test")
			.contentType("text/plain")
			.content(body))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("item_name"))
			.andExpect(jsonPath("$.id").exists())
			.andReturn();

		// Extract the ID from the response
		String id = JsonPath.read(postResult.getResponse().getContentAsString(), "$.id").toString();

		mockMvc.perform(get("/api/test/" + id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("item_name"))
			.andExpect(jsonPath("$.id").value(id));
	}

}
