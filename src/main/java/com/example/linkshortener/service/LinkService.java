package com.example.linkshortener.service;

import com.example.linkshortener.dao.LinkRepository;
import com.example.linkshortener.dao.entity.Link;
import com.example.linkshortener.exception.LinkNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {
    private final LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    public Link getLinkById(Long id) {
        return linkRepository.findById(id).orElseThrow(() -> new LinkNotFoundException(String.format("Link of ID %s does not exists.", id)));
    }

    public Link saveLink(Link link) {
        return linkRepository.save(link);
    }

    public Link updateLink(Long id, Link link) {
        link.setLinkId(id);
        return linkRepository.save(link);
    }

    public void deleteLinkById(Long id) {
        linkRepository.deleteById(id);
    }
}
