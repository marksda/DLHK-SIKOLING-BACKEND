package com.cso.sikoling.main.repository.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPengurusPermohonan;
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
public class KategoriPengurusPermohonanRepositoryEJB implements Repository<KategoriPengurusPermohonan, QueryParamFilters, Filter> {
    
    @Inject
    private KategoriPengurusPermohonanRepositoryJPA kategoriPengurusPermohonanRepositoryJPA;

    @Override
    public KategoriPengurusPermohonan updateId(String idLama, KategoriPengurusPermohonan t) throws SQLException {
        return kategoriPengurusPermohonanRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public KategoriPengurusPermohonan save(KategoriPengurusPermohonan t) throws SQLException {
        return kategoriPengurusPermohonanRepositoryJPA.save(t);
    }

    @Override
    public KategoriPengurusPermohonan update(KategoriPengurusPermohonan t) throws SQLException {
        return kategoriPengurusPermohonanRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return kategoriPengurusPermohonanRepositoryJPA.delete(id);
    }

    @Override
    public List<KategoriPengurusPermohonan> getDaftarData(QueryParamFilters q) {
        return kategoriPengurusPermohonanRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return kategoriPengurusPermohonanRepositoryJPA.getJumlahData(f);
    }
    
}
