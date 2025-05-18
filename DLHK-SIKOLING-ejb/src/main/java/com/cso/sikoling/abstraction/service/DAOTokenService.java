
package com.cso.sikoling.abstraction.service;

import com.cso.sikoling.abstraction.entity.Credential;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import io.jsonwebtoken.Claims;
import java.sql.SQLException;


public interface DAOTokenService<T> extends DAOService<T> {
    T getToken(Credential c) throws SQLException;
    Claims validateAccessToken(String accessToken);
    Key generateKey(String idRealm, String idJwa);
}
