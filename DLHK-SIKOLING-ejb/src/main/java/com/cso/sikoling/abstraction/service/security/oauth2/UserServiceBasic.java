package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.User;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikoling.main.util.GeneratorID;
import com.cso.sikoling.main.util.oauth2.PasswordHasher;
import java.security.SecureRandom;
import com.password4j.types.Argon2;


public class UserServiceBasic implements Service<User> {
    
    private final Repository<User, QueryParamFilters, Filter> repository;
    private final SecureRandom random = new SecureRandom();

    public UserServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public User save(User t) throws SQLException {  
        byte[] salt = getRandomSalt(new byte[16]);
        String hashPassword = PasswordHasher.getArgon2(
                t.getPassword(), 
                15, 
                32, 
                2, 
                48, 
                Argon2.ID,
                19,
                salt,
                null
            );
        User user = new User(GeneratorID.getUserId(), t.getUser_name(), 
                hashPassword, t.getTanggal_registrasi());
        
        return repository.save(user);
    }

    @Override
    public User update(User t) throws SQLException { 
        byte[] salt = getRandomSalt(new byte[16]);
        String hashPassword = PasswordHasher.getArgon2(
                t.getPassword(), 
                15, 
                32, 
                2, 
                48, 
                Argon2.ID,
                19,
                salt,
                null
            );
        User user = new User(t.getId(), t.getUser_name(), hashPassword, t.getTanggal_registrasi());
        return repository.update(user);
    }

    @Override
    public User updateId(String idLama, User t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<User> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }
    
    private synchronized byte[] getRandomSalt(byte[] salt) {
        random.nextBytes(salt);
        return salt;
    }

}
