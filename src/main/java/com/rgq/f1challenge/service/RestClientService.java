package com.rgq.f1challenge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClientService {
    private final RestTemplate restTemplate;

    public RestClientService() {
        restTemplate = new RestTemplate();
    }

    public String getHtml(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    public byte[] getImage(String url) {
        return restTemplate.getForObject(url, byte[].class);
    }
}
