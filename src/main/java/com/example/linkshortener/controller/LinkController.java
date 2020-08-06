package com.example.linkshortener.controller;

import com.example.linkshortener.dao.entity.Link;
import com.example.linkshortener.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/links")
public class LinkController {
    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public List<Link> getAllLinks() {
        return linkService.getAllLinks();
    }

    @GetMapping("/{id}")
    public Link getLinkById(@PathVariable(name = "id") Long id) {
        return linkService.getLinkById(id);
    }

    @PostMapping
    public Link createLink(@RequestBody Link link) {
        return linkService.saveLink(link);
    }

    @PutMapping("/{id}")
    public Link updateLink(@PathVariable(name = "id") Long id, @RequestBody Link link) {
        return linkService.updateLink(id, link);
    }

    @DeleteMapping
    public void deleteLinkById(@PathVariable(name = "id") Long id) {
        linkService.deleteLinkById(id);
    }
}
