package com.ortec.gta.service.impl;

import com.ortec.gta.service.HttpService;
import com.ortec.gta.shared.RestHttpRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: romain.pillot
 * @Date: 12/09/2017
 */

@Service
public class HttpServiceImpl implements HttpService {
    private final RestTemplate template;

    public HttpServiceImpl() {
        this.template = new RestTemplate();
    }

    public RestHttpRequest get(String url, Object... args) {
        return new RestHttpRequest(template, String.format(url, args), HttpMethod.GET);
    }

    public RestHttpRequest post(String url, Object object) {
        return new RestHttpRequest(template, url, HttpMethod.POST).withBody(object);
    }

    public RestHttpRequest put(String url, Object object) {
        return new RestHttpRequest(template, url, HttpMethod.PUT).withBody(object);
    }

    public RestHttpRequest delete(String url, Object...args) {
        return new RestHttpRequest(template, String.format(url, args), HttpMethod.DELETE);
    }
}
