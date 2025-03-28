package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriModelPerizinan;
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
public class KategoriModelPerizinanRepositoryEJB implements Repository<KategoriModelPerizinan, QueryParamFilters, Filter> {
    
    @Inject
    private KategoriModelPerizinanRepositoryJPA kategoriModelPerizinanRepositoryJPA;

    @Override
    public KategoriModelPerizinan updateId(String idLama, KategoriModelPerizinan t) throws SQLException {
        return kategoriModelPerizinanRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public KategoriModelPerizinan save(KategoriModelPerizinan t) throws SQLException {
        return kategoriModelPerizinanRepositoryJPA.save(t);
    }

    @Override
    public KategoriModelPerizinan update(KategoriModelPerizinan t) throws SQLException {
        return kategoriModelPerizinanRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return kategoriModelPerizinanRepositoryJPA.delete(id);
    }

    @Override
    public List<KategoriModelPerizinan> getDaftarData(QueryParamFilters q) {
        return kategoriModelPerizinanRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return kategoriModelPerizinanRepositoryJPA.getJumlahData(f);
    }
    
}
