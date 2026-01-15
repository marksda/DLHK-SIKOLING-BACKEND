package com.cso.sikoling.abstraction.service.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumenSementara;
import com.cso.sikoling.abstraction.repository.RegisterDokumenRepository;
import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.Json;
import java.util.Calendar;
import java.util.Date;


public class RegisterDokumenSementaraServiceBasic implements Service<RegisterDokumenSementara> {
    
    private final RegisterDokumenRepository<RegisterDokumenSementara, QueryParamFilters, Filter> repository;

    public RegisterDokumenSementaraServiceBasic(RegisterDokumenRepository repository) {
        this.repository = repository;
    }

    @Override
    public RegisterDokumenSementara save(RegisterDokumenSementara t) throws SQLException {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();      
        String id = repository.generateId();
        JsonObject metaFile = t.getMetaFile();
        String[] hasilSplit = metaFile.getString("BaseFileName").split("\\.");
        String namaFile = id.concat(".").concat(hasilSplit[hasilSplit.length-1]);
        JsonString jsonString = Json.createValue(namaFile);
        metaFile.replace("BaseFileName", jsonString);
        
        RegisterDokumenSementara tTemp = new RegisterDokumenSementara(
                id, 
                t.getIdJenisDokumen(), 
                t.getIdPerusahaan(),
                namaFile, 
                today,
                metaFile
            );
        
        return repository.save(tTemp);
    }

    @Override
    public RegisterDokumenSementara update(RegisterDokumenSementara t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public RegisterDokumenSementara updateId(String idLama, RegisterDokumenSementara t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<RegisterDokumenSementara> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
