package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
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
public class KategoriSkalaUsahaRepositoryEJB implements Repository<KategoriSkalaUsaha, QueryParamFilters, Filter> {
    
    @Inject
    private KategoriSkalaUsahaRepositoryJPA kategoriSkalaUsahaRepositoryJPA;

    @Override
    public KategoriSkalaUsaha updateId(String idLama, KategoriSkalaUsaha t) throws SQLException {
        return kategoriSkalaUsahaRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public KategoriSkalaUsaha save(KategoriSkalaUsaha t) throws SQLException {
        return kategoriSkalaUsahaRepositoryJPA.save(t);
    }

    @Override
    public KategoriSkalaUsaha update(KategoriSkalaUsaha t) throws SQLException {
        return kategoriSkalaUsahaRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return kategoriSkalaUsahaRepositoryJPA.delete(id);
    }

    @Override
    public List<KategoriSkalaUsaha> getDaftarData(QueryParamFilters q) {
        return kategoriSkalaUsahaRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return kategoriSkalaUsahaRepositoryJPA.getJumlahData(f);
    }
    
}
