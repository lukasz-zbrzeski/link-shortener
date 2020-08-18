package com.example.linkshortener.controller;

import com.example.linkshortener.dao.entity.Link;
import com.example.linkshortener.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WebPageController {

    private final LinkService linkService;

    @Autowired
    public WebPageController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public String form(@ModelAttribute("link") Link link) {
        return "form";
    }

    @PostMapping("/shorted-link")
    public String shortedLink(@ModelAttribute("link") Link link, HttpServletRequest request) {
        linkService.saveLink(link, request);
        return "shorted-link";
    }
}
