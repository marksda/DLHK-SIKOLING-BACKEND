package com.cso.sikoling.abstraction.service.alamat;

import com.cso.sikoling.abstraction.repository.PropinsiRepository;
import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Propinsi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import java.sql.SQLException;
import java.util.List;

public class PropinsiServiceBasic implements PropinsiService {
    
    private final PropinsiRepository propinsiRepository;

    public PropinsiServiceBasic(PropinsiRepository propinsiRepository) {
        this.propinsiRepository = propinsiRepository;
    }

    @Override
    public Propinsi save(Propinsi t) throws SQLException {
        return propinsiRepository.save(t);
    }

    @Override
    public Propinsi update(Propinsi t) throws SQLException {
        return propinsiRepository.update(t);
    }
    

    @Override
    public boolean delete(String id) throws SQLException {
        return propinsiRepository.delete(id);
    }

    @Override
    public List<Propinsi> getDaftarData(QueryParamFilters queryParamFilters) {
        return propinsiRepository.getDaftarData(queryParamFilters);
    }

    @Override
    public Long getJumlahData(List<Filter> queryParamFilters) {
        return propinsiRepository.getJumlahData(queryParamFilters);
    }

    @Override
    public Propinsi updateId(String idLama, Propinsi t) throws SQLException {
        return propinsiRepository.updateId(idLama, t);
    }

}
