package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.dokumen.MasterDokumen;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
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
public class MasterDokumenRepositoryEJB implements Repository<MasterDokumen, QueryParamFilters, Filter> {
    
    @Inject
    private MasterDokumenRepositoryJPA dokumenRepositoryJPA;

    @Override
    public MasterDokumen updateId(String idLama, MasterDokumen t) throws SQLException {
        return dokumenRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public MasterDokumen save(MasterDokumen t) throws SQLException {
        return dokumenRepositoryJPA.save(t);
    }

    @Override
    public MasterDokumen update(MasterDokumen t) throws SQLException {
        return dokumenRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return dokumenRepositoryJPA.delete(id);
    }

    @Override
    public List<MasterDokumen> getDaftarData(QueryParamFilters q) {
        return dokumenRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return dokumenRepositoryJPA.getJumlahData(f);
    }
    
}
