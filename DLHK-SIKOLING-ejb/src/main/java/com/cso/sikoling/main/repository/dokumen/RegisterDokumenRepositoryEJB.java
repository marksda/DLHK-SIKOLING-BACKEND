package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumen;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.main.Infrastructure;
import jakarta.ejb.Local;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@Stateless
@Local
@Infrastructure
public class RegisterDokumenRepositoryEJB implements Repository<RegisterDokumen, QueryParamFilters, Filter> {
    
    @Inject
    private RegisterDokumenRepositoryJPA repositoryJPA;

    @Override
    public RegisterDokumen updateId(String idLama, RegisterDokumen t) throws SQLException {
        return repositoryJPA.updateId(idLama, t);
    }

    @Override
    public RegisterDokumen save(RegisterDokumen t) throws SQLException {
        return repositoryJPA.save(t);
    }

    @Override
    public RegisterDokumen update(RegisterDokumen t) throws SQLException {
        return repositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repositoryJPA.delete(id);
    }

    @Override
    public List<RegisterDokumen> getDaftarData(QueryParamFilters q) {
        return repositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return repositoryJPA.getJumlahData(f);
    }
    
}
