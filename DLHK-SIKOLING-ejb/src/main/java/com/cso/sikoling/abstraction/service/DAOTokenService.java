
package com.cso.sikoling.abstraction.service;

import com.cso.sikoling.abstraction.entity.Credential;
import java.sql.SQLException;


public interface DAOTokenService<T> extends DAOService<T> {
    T getToken(Credential c) throws SQLException;
}
