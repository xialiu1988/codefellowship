package com.shuai88qi.xl2019.codefellowship;

import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CodefellowshipApplicationTests {
	@Autowired
	cfShipController cfShipController;

	@Autowired
	MockMvc mockMvc;


	@Test
	public void contextLoads() {
	}



	@Test
	public void testControllerIsAutowired() {
		assertNotNull(cfShipController);
	}


	@Test
	public void testHomeRoute() throws Exception{
		mockMvc.perform(get("/")).andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void testsignupRoute() throws Exception{
		mockMvc.perform(get("/signup")).andDo(print())
				.andExpect(status().isOk());

	}


}
