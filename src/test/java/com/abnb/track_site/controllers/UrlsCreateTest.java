package com.abnb.track_site.controllers;

import com.abnb.track_site.Main;
import com.abnb.track_site.model.Url;
import com.abnb.track_site.repository.UrlRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
@ActiveProfiles(profiles = {"default", "test"})
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UrlsCreateTest {

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
     * Test of create method, of class Urls.
     */
    @Test
    public void testCreate() throws Exception {
        Url url = new Url();
        url.setName("Google");
        url.setAddress("http://www.google.com");
        String jsonContent = mapper.writeValueAsString(url);
        MvcResult result = mockMvc.perform(post("/urls/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andReturn();
        Integer id = mapper.readValue(result.getResponse().getContentAsByteArray(), Integer.class);
        Url returnUrl = urlRepository.findOne(id);
        Assert.assertEquals(returnUrl.getId(), id);
    }

}
