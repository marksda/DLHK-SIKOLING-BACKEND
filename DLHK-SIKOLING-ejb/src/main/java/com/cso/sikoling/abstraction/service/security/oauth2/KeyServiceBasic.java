package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import com.cso.sikoling.abstraction.repository.Repository;
import com.github.f4b6a3.uuid.UuidCreator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKey;
import com.cso.sikoling.abstraction.service.KeyService;
import com.cso.sikoling.main.util.oauth2.KeyToolGenerator;
import io.jsonwebtoken.security.Curve;
import io.jsonwebtoken.security.Jwks;
import java.util.Date;


public class KeyServiceBasic implements KeyService<Key> {
    
    private final Repository<Key, QueryParamFilters, Filter> repository;

    public KeyServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public Key save(Key t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public Key update(Key t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public Key updateId(String idLama, Key t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<Key> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }
    
    @Override
    public Key generateKey(String idRealm, String idJwa, String encodingScheme) {
        return KeyToolGenerator.generateKey(idRealm, idJwa, encodingScheme);
    }
    
}