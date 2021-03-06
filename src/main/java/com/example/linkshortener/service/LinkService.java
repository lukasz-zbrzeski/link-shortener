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

    private static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    public Link getLinkById(Long id) {
        return linkRepository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(String.format("Link of ID %s does not exists.", id)));
    }

    public Link getLinkByShortLink(String shortLink) {
        return linkRepository.findByShortLink(shortLink)
                .orElseThrow(() -> new LinkNotFoundException(String.format("Link %s does not exists.", shortLink)));
    }

    public Link saveLink(Link link, HttpServletRequest request) {
        link.setShortLink(createShortLink(request));
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

    public String getShortLink(String uri, HttpServletRequest request) {
        String serverPort = request.getServerName().equals("localhost") || request.getServerName().equals("127.0.0.1")
                ? String.format(":%s", request.getServerPort())
                : "";
        return String.format("%s://%s%s/%s", request.getScheme(), request.getServerName(), serverPort, uri);
    }

    private String createShortLink(HttpServletRequest request) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        return getShortLink(sb.toString(), request);
    }
}
