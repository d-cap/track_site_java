package com.abnb.track_site.controllers;

import com.abnb.track_site.model.Url;
import com.abnb.track_site.repository.UrlRepository;
import javax.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/urls")
public class UrlsController {

    @Resource
    private UrlRepository urlRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Url> index() {
        return urlRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Url show(@PathVariable(value = "id") final Integer id) {
        return urlRepository.findOne(id);
    }

}
