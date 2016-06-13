package com.abnb.track_site.controllers;

import com.abnb.track_site.model.Url;
import com.abnb.track_site.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/urls")
@ExposesResourceFor(Url.class)
@Transactional
public class UrlsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlsController.class);

    private final UrlRepository urlRepository;

    private final EntityLinks entityLinks;

    @Autowired
    public UrlsController(UrlRepository urlRepository, EntityLinks entityLinks) {
        this.urlRepository = urlRepository;
        this.entityLinks = entityLinks;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resources<Resource<Url>>> index() {
        Iterable<Url> urls = urlRepository.findAll();
        List<Resource<Url>> urlsResource = new ArrayList<>();
        for (Url url : urls) {
            urlsResource.add(getUrlResource(url.getId(), url));
        }
        Resources<Resource<Url>> resources = new Resources<>(urlsResource);
        resources.add(entityLinks.linkToCollectionResource(Url.class).withSelfRel());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<Url>> show(@PathVariable(value = "id") final Integer id) {
        ResponseEntity<Resource<Url>> result;
        LOGGER.debug("start");
        Url url = urlRepository.findOne(id);
        LOGGER.debug("url: {}", url);
        if (url == null) {
            result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Resource<Url> resource = getUrlResource(id, url);
            result = new ResponseEntity<>(resource, HttpStatus.OK);
        }
        LOGGER.debug("result: {}", result);
        return result;
    }

    private Resource<Url> getUrlResource(Integer id, Url url) {
        Resource<Url> resource = new Resource<>(url);
        resource.add(entityLinks.linkToSingleResource(Url.class, id).withSelfRel());
        return resource;
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
        } catch (EmptyResultDataAccessException e) {
            result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
