
package com.cso.sikoling.abstraction.repository;

import com.cso.sikoling.abstraction.entity.Credential;
import io.jsonwebtoken.Claims;

public interface TokenRepository<T, Q, F> extends Repository<T, Q, F> {
    T getToken(Credential c, String idRealm, String idKey, String encodingScheme);
    Claims validateAccessToken(String accessToken);
}