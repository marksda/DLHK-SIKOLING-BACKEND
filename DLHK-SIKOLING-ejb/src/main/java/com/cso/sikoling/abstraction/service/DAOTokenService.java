
package com.cso.sikoling.abstraction.service;

import com.cso.sikoling.abstraction.entity.Credential;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import io.jsonwebtoken.Claims;


public interface DAOTokenService<T> extends DAOService<T> {
    T getToken(Credential c, String idRealm, String idKey, String encodingScheme);
    Claims validateAccessToken(String accessToken);
    Key generateKey(String idRealm, String idJwa, String encodingScheme);
}
