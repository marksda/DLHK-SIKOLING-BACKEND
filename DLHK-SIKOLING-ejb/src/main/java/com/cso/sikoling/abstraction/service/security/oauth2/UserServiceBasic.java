package com.cso.sikoling.abstraction.service.security.oauth2;

import com.cso.sikoling.abstraction.entity.Credential;
import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.User;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.UserService;
import com.cso.sikoling.main.util.GeneratorID;
import com.cso.sikoling.main.util.oauth2.PasswordHasher;
import com.password4j.SaltGenerator;
import com.password4j.types.Argon2;
import com.password4j.types.Bcrypt;
import java.util.ArrayList;


public class UserServiceBasic implements UserService<User> {
    
    private final Repository<User, QueryParamFilters, Filter> repository;

    public UserServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public User save(User t) throws SQLException {  
        byte[] salt = SaltGenerator.generate(16);
//        --argon2--
        String hashPassword = PasswordHasher.getArgon2(t.getPassword(), 15, 32, 2, 48, Argon2.ID, 19, salt, null);
//        --CompressedPBKDF2--
//        String hashPassword = PasswordHasher.getCompressedPBKDF2(t.getPassword(), 1000, 256, Hmac.SHA256, salt, null);
//        --Scrypt--
//        String hashPassword = PasswordHasher.getScrypt(t.getPassword(), 16384, 8, 2, 128, salt, null);
//        --Bcrypt--
//        String hashPassword = PasswordHasher.getBcrypt(t.getPassword(), Bcrypt.Y, null, 11);
        User user = new User(GeneratorID.getUserId(), t.getUser_name(), 
                hashPassword, t.getTanggal_registrasi());
        
        return repository.save(user);
    }

    @Override
    public User update(User t) throws SQLException { 
        byte[] salt = SaltGenerator.generate(16);
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

    @Override
    public boolean authentication(Credential credential) {
        boolean verified;
        List<Filter> fields_filter = new ArrayList<>();
        Filter filter = new Filter("nama", credential.getEmail());
        fields_filter.add(filter);
        QueryParamFilters qFilter = new QueryParamFilters(false, null, fields_filter, null);
        
        List<User> daftaruser = repository.getDaftarData(qFilter);
        if(daftaruser.isEmpty()) {
            verified = false;
        }
        else{
            String hashFromDB = daftaruser.getFirst().getPassword();            
            verified = PasswordHasher.checkArgon2(credential.getPassword(), hashFromDB, null);
//            verified = PasswordHasher.checkCompressedPBKDF2(credential.getPassword(), hashFromDB, null);
//            verified = PasswordHasher.checkScrypt(credential.getPassword(), hashFromDB, null);
//            verified = PasswordHasher.checkBcrypt(credential.getPassword(), hashFromDB, null);
        }
        
        return verified;
    }

}
