package com.abnb.track_site.controllers;

import com.abnb.track_site.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class UrlsTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before

	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * Test of index method, of class Urls.
	 */
	@Test
	public void testIndex() throws Exception {
		mockMvc.perform(get("/urls/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	/**
	 * Test of show method, of class Urls.
	 */
	@Test
	public void testShow() throws Exception {
		mockMvc.perform(get("/urls/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
