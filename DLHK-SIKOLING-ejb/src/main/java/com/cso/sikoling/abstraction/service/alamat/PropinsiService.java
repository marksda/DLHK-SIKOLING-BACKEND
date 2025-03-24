package com.cso.sikoling.abstraction.service.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Propinsi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import java.sql.SQLException;
import java.util.List;

public interface PropinsiService {
    Propinsi save(Propinsi t) throws SQLException;
    Propinsi update(Propinsi t) throws SQLException;
    Propinsi updateId(String idLama, Propinsi t) throws SQLException;
    boolean delete(String id) throws SQLException;
    List<Propinsi> getDaftarData(QueryParamFilters queryParamFilters);
    Long getJumlahData(List<Filter> queryParamFilters);
}