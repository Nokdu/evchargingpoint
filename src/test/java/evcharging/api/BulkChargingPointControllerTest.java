package evcharging.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import evcharging.api.Application;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@EnableWebMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class BulkChargingPointControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Test
	public void test() throws Exception {

		MockMultipartFile csvFile = new MockMultipartFile("file", "charging-points.csv", "multipart/form-data",
				"some csv".getBytes());

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/bulk-charging-point/save-csv").file(csvFile))
				.andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}
}
