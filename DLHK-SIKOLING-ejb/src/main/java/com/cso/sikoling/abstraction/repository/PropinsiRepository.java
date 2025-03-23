package com.cso.sikoling.abstraction.repository;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Propinsi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import java.sql.SQLException;

public interface PropinsiRepository extends Repository<Propinsi, QueryParamFilters, Filter> {
    Propinsi updateId(String idLama, Propinsi t) throws SQLException;
}
