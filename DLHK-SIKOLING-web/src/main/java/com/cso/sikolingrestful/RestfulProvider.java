package com.cso.sikolingrestful;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Propinsi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.abstraction.service.alamat.PropinsiService;
import com.cso.sikoling.abstraction.service.alamat.PropinsiServiceBasic;
import com.cso.sikoling.main.Infrastructure;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.inject.Produces;

@Stateless
@LocalBean
public class RestfulProvider {

    @Produces
    public PropinsiService getPropinsiService(
            @Infrastructure Repository<Propinsi, QueryParamFilters, Filter> propinsiRepository) {
        return new PropinsiServiceBasic(propinsiRepository);
    }
}
