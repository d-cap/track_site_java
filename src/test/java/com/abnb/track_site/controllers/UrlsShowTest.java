package com.abnb.track_site.controllers;

import com.abnb.track_site.Main;
import com.abnb.track_site.model.Url;
import com.abnb.track_site.repository.UrlRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
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

import org.springframework.test.web.servlet.MvcResult;
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
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Test of show method, of class Urls.
     */
    @Test
    public void testShow() throws Exception {
        Url url = getUrl();
        String jsonContent = mapper.writeValueAsString(url);
        MvcResult result = mockMvc.perform(get("/urls/" + url.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent))
                .andReturn();
        Url returnUrl = mapper.readValue(result.getResponse().getContentAsByteArray(), Url.class);
        Assert.assertEquals(returnUrl, url);
    }

    @Test
    public void testShowNotFound() throws Exception {
        mockMvc.perform(get("/urls/" + 0).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    private Url getUrl() throws IOException, NoSuchAlgorithmException {
        Url url = new Url();
        url.setAddress("http://www.google.com");
        url.setName("Google");
        url = urlRepository.save(url);
        return url;
    }

}
