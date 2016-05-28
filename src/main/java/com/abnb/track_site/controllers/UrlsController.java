package com.abnb.track_site.controllers;

import com.abnb.track_site.model.Url;
import com.abnb.track_site.repository.UrlRepository;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/urls")
public class UrlsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlsController.class);

    @Resource
    private UrlRepository urlRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Url> index() {
        return urlRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Url>show(@PathVariable(value = "id") final Integer id) {
        ResponseEntity<Url> result;
        LOGGER.debug("start");
        Url url = urlRepository.findOne(id);
        LOGGER.debug("url: {}", url);
        if (url == null) {
            result =  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            result = new ResponseEntity<>(url, HttpStatus.OK);
        }
        LOGGER.debug("result: {}", result);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer create(@Validated @RequestBody Url url) {
        return urlRepository.save(url).getId();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> destroy(@PathVariable(value = "id") final Integer id) {
        ResponseEntity<String> result;
        try {
            urlRepository.delete(id);
            result = new ResponseEntity<>(HttpStatus.OK);
        } catch(EmptyResultDataAccessException e) {
            result =  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable(value = "id") final Integer id, @RequestBody Url sentUrl) {
        Url url = urlRepository.findOne(id);
        url.update(sentUrl);
        urlRepository.save(url);
    }

}
