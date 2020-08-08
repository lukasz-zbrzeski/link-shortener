package com.example.linkshortener.service;

import com.example.linkshortener.dao.LinkRepository;
import com.example.linkshortener.dao.entity.Link;
import com.example.linkshortener.exception.LinkNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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

    public Link saveLink(Link link, HttpServletRequest request) {
        String serverPort = request.getServerName().equals("localhost") ? String.format(":%s", request.getServerPort()) : "";;
        String shortLink = String.format("%s://%s%s/%s", request.getScheme(), request.getServerName(), serverPort, createShortLink());
        link.setShortLink(shortLink);
        link.setCreatedAt(LocalDateTime.now());
        link.setExpiresAt(LocalDateTime.now().plusMonths(1));
        link.setActive(true);
        return linkRepository.save(link);
    }

    public Link updateLink(Long id, Link link) {
        link.setLinkId(id);
        return linkRepository.save(link);
    }

    public void deleteLinkById(Long id) {
        if (linkRepository.findById(id).isPresent()) {
            linkRepository.deleteById(id);
        } else {
            throw new LinkNotFoundException(String.format("Link of ID %s does not exists.", id));
        }
    }

    private String createShortLink() {
        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(digits.charAt(random.nextInt(digits.length())));
        }
        return sb.toString();
    }
}
