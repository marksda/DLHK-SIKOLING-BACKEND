package com.cso.sikoling.abstraction.service.permohonan;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPermohonan;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.permohonan.PosisiTahapPemberkasan;
import com.cso.sikoling.abstraction.repository.Repository;

import java.sql.SQLException;
import java.util.List;
import com.cso.sikoling.abstraction.service.Service;


public class PosisiTahapPemberkasanServiceBasic implements Service<PosisiTahapPemberkasan> {
    
    private final Repository<PosisiTahapPemberkasan, QueryParamFilters, Filter> repository;

    public PosisiTahapPemberkasanServiceBasic(Repository repository) {
        this.repository = repository;
    }    

    @Override
    public PosisiTahapPemberkasan save(PosisiTahapPemberkasan t) throws SQLException {
        return repository.save(t);
    }

    @Override
    public PosisiTahapPemberkasan update(PosisiTahapPemberkasan t) throws SQLException {
        return repository.update(t);
    }

    @Override
    public PosisiTahapPemberkasan updateId(String idLama, PosisiTahapPemberkasan t) throws SQLException {
        return repository.updateId(idLama, t);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return repository.delete(id);
    }

    @Override
    public List<PosisiTahapPemberkasan> getDaftarData(QueryParamFilters queryParamFilters) {
        return repository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return repository.getJumlahData(queryParamFilters);
    }

}
