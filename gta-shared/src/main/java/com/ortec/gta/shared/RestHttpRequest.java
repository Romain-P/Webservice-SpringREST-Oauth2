package com.ortec.gta.shared;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RestHttpRequest {
    private final String url;
    private final HttpHeaders headers;
    private final HttpMethod method;
    private final RestTemplate template;
    private Object object;

    public RestHttpRequest(RestTemplate template, String url, HttpMethod method) {
        this.method = method;
        this.url = url;
        this.headers = new HttpHeaders();
        this.template = template;
    }

    public RestHttpRequest withContentType(MediaType type) {
        this.headers.setContentType(type);
        return this;
    }

    public RestHttpRequest accepts(MediaType... types) {
        this.headers.setAccept(Arrays.asList(types));
        return this;
    }

    public RestHttpRequest addHeader(String key, String value) {
        this.headers.set(key, value);
        return this;
    }

    public RestHttpRequest withBody(Object object) {
        this.object = object;
        return this;
    }

    public void toResponse() {
        HttpEntity<?> body = new HttpEntity<>(object, headers);
            /* ignore returned value */
        template.exchange(url, method, body, Object.class);
    }

    public <T> T toResponse(Class<T> responseType) {
        HttpEntity<?> body = new HttpEntity<>(object, headers);
        ResponseEntity<T> response = template.exchange(url, method, body, responseType);
        return response.getBody();
    }

    public <T> Set<T> toResponseSet(Class<T[]> setType) {
        HttpEntity<?> body = new HttpEntity<>(object, headers);
        ResponseEntity<T[]> response = template.exchange(url, method, body, setType);
        return Sets.newHashSet(response.getBody());
    }

    public <T> List<T> toResponseList(Class<T[]> setType) {
        HttpEntity<?> body = new HttpEntity<>(object, headers);
        ResponseEntity<T[]> response = template.exchange(url, method, body, setType);
        return Lists.newArrayList(response.getBody());
    }
}