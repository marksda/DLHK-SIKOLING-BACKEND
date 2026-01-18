package com.cso.sikoling.abstraction.service.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumen;
import com.cso.sikoling.abstraction.repository.Repository;
import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;
import jakarta.json.JsonValue;
import java.util.Date;


public class RegisterDokumenServiceBasic implements Service<RegisterDokumen> {
    
    private final Repository<RegisterDokumen, QueryParamFilters, Filter> repository;

    public RegisterDokumenServiceBasic(Repository repository) {
        this.repository = repository;
    }

    @Override
    public RegisterDokumen save(RegisterDokumen t) throws SQLException {
        if(t.getTanggalRegistrasi() == null) {
            RegisterDokumen regDok = new RegisterDokumen(
                    t.getId(), 
                    t.getPerusahaan(),
                    t.getDokumen(), 
                    new Date(), 
                    t.getUploader(), 
                    t.getNamaFile(), 
                    t.getStatusDokumen(), 
                    t.getIdLama(), 
                    t.getIsValidated(), 
                    t.getMetaFile(),
                    t.getMetaInfo()
                );
            return repository.save(regDok);
        }
        else {
            return repository.save(t);
        }        
    }

    @Override
    public RegisterDokumen update(RegisterDokumen t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public RegisterDokumen updateId(String idLama, RegisterDokumen t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<RegisterDokumen> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
