
package com.cso.sikoling.abstraction.repository;

import com.cso.sikoling.abstraction.entity.Credential;
import io.jsonwebtoken.Claims;
import java.sql.SQLException;

public interface RepositoryToken<T, Q, F> extends Repository<T, Q, F> {
    T getToken(Credential c) throws SQLException;
    Claims validateAccessToken(String accessToken);
}
