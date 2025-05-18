
package com.cso.sikoling.abstraction.repository;

import com.cso.sikoling.abstraction.entity.Credential;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import io.jsonwebtoken.Claims;
import java.sql.SQLException;

public interface RepositoryToken<T, Q, F> extends Repository<T, Q, F> {
    T getToken(Credential c, String idKey, String encodedType) throws SQLException;
    Claims validateAccessToken(String accessToken);
    Key generateKey(String idRealm, String idJwa, String encodedType);
}