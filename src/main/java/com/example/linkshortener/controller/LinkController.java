package com.example.linkshortener.controller;

import com.example.linkshortener.dao.entity.Link;
import com.example.linkshortener.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/links",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class LinkController {
    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public ResponseEntity<List<Link>> getAllLinks() {
        return ResponseEntity.ok(linkService.getAllLinks());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Link> getLinkById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(linkService.getLinkById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Link> createLink(@RequestBody Link link) {
        return ResponseEntity.ok(linkService.saveLink(link));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Link> updateLink(@PathVariable(name = "id") Long id, @RequestBody Link link) {
        return ResponseEntity.ok(linkService.updateLink(id, link));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteLinkById(@PathVariable(name = "id") Long id) {
        linkService.deleteLinkById(id);
    }
}
