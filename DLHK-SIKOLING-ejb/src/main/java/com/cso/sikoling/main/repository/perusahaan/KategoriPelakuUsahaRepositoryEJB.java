package com.cso.sikoling.main.repository.perusahaan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriPelakuUsaha;
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
public class KategoriPelakuUsahaRepositoryEJB implements Repository<KategoriPelakuUsaha, QueryParamFilters, Filter> {
    
    @Inject
    private KategoriPelakuUsahaRepositoryJPA kategoriPelakuUsahaRepositoryJPA;

    @Override
    public KategoriPelakuUsaha updateId(String idLama, KategoriPelakuUsaha t) throws SQLException {
        return kategoriPelakuUsahaRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public KategoriPelakuUsaha save(KategoriPelakuUsaha t) throws SQLException {
        return kategoriPelakuUsahaRepositoryJPA.save(t);
    }

    @Override
    public KategoriPelakuUsaha update(KategoriPelakuUsaha t) throws SQLException {
        return kategoriPelakuUsahaRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return kategoriPelakuUsahaRepositoryJPA.delete(id);
    }

    @Override
    public List<KategoriPelakuUsaha> getDaftarData(QueryParamFilters q) {
        return kategoriPelakuUsahaRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return kategoriPelakuUsahaRepositoryJPA.getJumlahData(f);
    }
    
}
