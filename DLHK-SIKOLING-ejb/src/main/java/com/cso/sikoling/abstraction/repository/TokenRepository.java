
package com.cso.sikoling.abstraction.repository;

import io.jsonwebtoken.Claims;

public interface TokenRepository<T, Q, F> extends Repository<T, Q, F> {
    Claims validateAccessToken(String accessToken);
}