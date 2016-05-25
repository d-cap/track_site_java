package com.abnb.track_site.controllers;

import com.abnb.track_site.Main;
import com.abnb.track_site.model.Url;
import com.abnb.track_site.repository.UrlRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
@ActiveProfiles(profiles = {"default", "test"})
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UrlsShowTest {

    private ObjectMapper mapper;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UrlRepository urlRepository;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.mapper = new ObjectMapper();
    }

    /**
     * Test of show method, of class Urls.
     */
    @Test
    public void testShow() throws Exception {
        Url url = new Url();
        url.setAddress("http://www.google.com");
        url.setName("Google");
        url.setCreatedAt(new Date());
        url.setUpdatedAt(new Date());
        url = urlRepository.save(url);
        String jsonContent = mapper.writeValueAsString(url);
        mockMvc.perform(get("/urls/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }

}
