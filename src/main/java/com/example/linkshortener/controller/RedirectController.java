package com.example.linkshortener.controller;

import com.example.linkshortener.dao.entity.Link;
import com.example.linkshortener.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RedirectController {

    private final LinkService linkService;

    @Autowired
    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{uri}")
    public void redirect(@PathVariable(name = "uri") String uri, HttpServletRequest request, HttpServletResponse response) {
        String shortLink = linkService.getShortLink(uri, request);
        Link link = linkService.getLinkByShortLink(shortLink);
        response.addHeader("Location", link.getOriginalLink());
        response.setStatus(302);
    }

}
