package com.cso.sikoling.abstraction.service.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumenSementara;
import com.cso.sikoling.abstraction.repository.Repository;
import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;
import java.util.Calendar;
import java.util.Date;


public class RegisterDokumenSementaraServiceBasic implements Service<RegisterDokumenSementara> {
    
    private final Repository<RegisterDokumenSementara, QueryParamFilters, Filter> repository;

    public RegisterDokumenSementaraServiceBasic(Repository repository) {
        this.repository = repository;
    }

    @Override
    public RegisterDokumenSementara save(RegisterDokumenSementara t) throws SQLException {
        if(t.getTanggal() == null) {
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            RegisterDokumenSementara tTemp = new RegisterDokumenSementara(
                    t.getId(), 
                    t.getIdJenisDokumen(), 
                    t.getIdPerusahaan(),
                    t.getNamaFile(), 
                    today,
                    t.getMetaFile()
            );
            return repository.save(tTemp);
        }
        else {
            return repository.save(t);
        }
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
