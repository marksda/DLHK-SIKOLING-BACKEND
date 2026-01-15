package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumenSementara;
import com.cso.sikoling.abstraction.repository.RegisterDokumenRepository;
import com.cso.sikoling.main.Infrastructure;
import jakarta.ejb.Local;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@Stateless
@Local
@Infrastructure
public class RegisterDokumenSementaraRepositoryEJB implements RegisterDokumenRepository<RegisterDokumenSementara, QueryParamFilters, Filter> {
    
    @Inject
    private RegisterDokumenSementaraRepositoryJPA repositoryJPA;

    @Override
    public RegisterDokumenSementara updateId(String idLama, RegisterDokumenSementara t) throws SQLException {
        return repositoryJPA.updateId(idLama, t);
    }

    @Override
    public RegisterDokumenSementara save(RegisterDokumenSementara t) throws SQLException {
        return repositoryJPA.save(t);
    }

    @Override
    public RegisterDokumenSementara update(RegisterDokumenSementara t) throws SQLException {
        return repositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repositoryJPA.delete(id);
    }

    @Override
    public List<RegisterDokumenSementara> getDaftarData(QueryParamFilters q) {
        return repositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return repositoryJPA.getJumlahData(f);
    }

    @Override
    public String generateId() {
        return repositoryJPA.generateId();
    }
    
}
