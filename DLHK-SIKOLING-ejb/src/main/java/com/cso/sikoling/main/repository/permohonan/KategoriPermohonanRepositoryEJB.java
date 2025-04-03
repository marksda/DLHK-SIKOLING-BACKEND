package com.cso.sikoling.main.repository.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPermohonan;
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
public class KategoriPermohonanRepositoryEJB implements Repository<KategoriPermohonan, QueryParamFilters, Filter> {
    
    @Inject
    private KategoriPermohonanRepositoryJPA kategoriPermohonanRepositoryJPA;

    @Override
    public KategoriPermohonan updateId(String idLama, KategoriPermohonan t) throws SQLException {
        return kategoriPermohonanRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public KategoriPermohonan save(KategoriPermohonan t) throws SQLException {
        return kategoriPermohonanRepositoryJPA.save(t);
    }

    @Override
    public KategoriPermohonan update(KategoriPermohonan t) throws SQLException {
        return kategoriPermohonanRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return kategoriPermohonanRepositoryJPA.delete(id);
    }

    @Override
    public List<KategoriPermohonan> getDaftarData(QueryParamFilters q) {
        return kategoriPermohonanRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return kategoriPermohonanRepositoryJPA.getJumlahData(f);
    }
    
}
