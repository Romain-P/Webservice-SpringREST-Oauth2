package com.ortec.gta.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 12/09/2017
 */

@Service
public class HttpService {
    private final RestTemplate template;

    public HttpService() {
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

    public static final class RestHttpRequest {
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
}
