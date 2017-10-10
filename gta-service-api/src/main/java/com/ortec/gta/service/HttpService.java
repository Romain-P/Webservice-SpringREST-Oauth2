package com.ortec.gta.service;

import com.ortec.gta.shared.RestHttpRequest;

/**
 * @Author: romain.pillot
 * @Date: 10/10/2017
 */
public interface HttpService {
    RestHttpRequest get(String url, Object... args);
    RestHttpRequest post(String url, Object object);
    RestHttpRequest put(String url, Object object);
    RestHttpRequest delete(String url, Object...args);
}
