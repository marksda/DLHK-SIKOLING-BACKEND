
package com.cso.sikoling.abstraction.service;

import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import io.jsonwebtoken.Claims;
import java.util.Map;


public interface TokenService<T> extends Service<T> {
    T generateToken(Key key, Map<String, Object> header, Map<String, Object> payload);
    Claims validateAccessToken(String accessToken);
}
