package com.cso.sikoling.main.repository.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.permohonan.PosisiTahapPemberkasan;
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
public class PosisiTahapPemberkasanRepositoryEJB implements Repository<PosisiTahapPemberkasan, QueryParamFilters, Filter> {
    
    @Inject
    private PosisiTahapPemberkasanRepositoryJPA posisiTahapPemberkasanRepositoryJPA;

    @Override
    public PosisiTahapPemberkasan updateId(String idLama, PosisiTahapPemberkasan t) throws SQLException {
        return posisiTahapPemberkasanRepositoryJPA.updateId(idLama, t);
    }

    @Override
    public PosisiTahapPemberkasan save(PosisiTahapPemberkasan t) throws SQLException {
        return posisiTahapPemberkasanRepositoryJPA.save(t);
    }

    @Override
    public PosisiTahapPemberkasan update(PosisiTahapPemberkasan t) throws SQLException {
        return posisiTahapPemberkasanRepositoryJPA.update(t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return posisiTahapPemberkasanRepositoryJPA.delete(id);
    }

    @Override
    public List<PosisiTahapPemberkasan> getDaftarData(QueryParamFilters q) {
        return posisiTahapPemberkasanRepositoryJPA.getDaftarData(q);
    }

    @Override
    public Long getJumlahData(List<Filter> f) {
        return posisiTahapPemberkasanRepositoryJPA.getJumlahData(f);
    }
    
}
