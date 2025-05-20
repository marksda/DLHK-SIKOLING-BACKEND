
package com.cso.sikoling.abstraction.repository;

public interface KeyRepository<T, Q, F> extends Repository<T, Q, F> {
    T generateKey(String idRealm, String idJwa, String encodingScheme);
}