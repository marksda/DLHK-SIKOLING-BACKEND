package com.cso.sikoling.abstraction.service;

import com.cso.sikoling.abstraction.entity.security.oauth2.Key;


public interface DAOKeyService<T> extends DAOService<T> {
    Key generateKey(String idRealm, String idJwa, String encodingScheme);
}
