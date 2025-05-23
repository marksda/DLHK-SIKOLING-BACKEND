
package com.cso.sikoling.abstraction.service;

import com.cso.sikoling.abstraction.entity.security.Autorisasi;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import io.jsonwebtoken.Claims;


public interface TokenService<T> extends Service<T> {
    T generateToken(Key key, Autorisasi autorisasi);
    Claims validateAccessToken(String accessToken);
}
