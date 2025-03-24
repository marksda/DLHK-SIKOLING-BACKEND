package com.cso.sikolingrestful;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.Kabupaten;
import com.cso.sikoling.abstraction.entity.Propinsi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.abstraction.service.alamat.PropinsiServiceBasic;
import com.cso.sikoling.main.Infrastructure;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.inject.Produces;
import com.cso.sikoling.abstraction.service.alamat.AlamatService;
import com.cso.sikoling.abstraction.service.alamat.KabupatenServiceBasic;

@Stateless
@LocalBean
public class RestfulProvider {

    @Produces
    public AlamatService<Propinsi> getPropinsiService(
            @Infrastructure Repository<Propinsi, QueryParamFilters, Filter> propinsiRepository) {
        return new PropinsiServiceBasic(propinsiRepository);
    }
    
    @Produces
    public AlamatService<Kabupaten> getKabupatenService(
            @Infrastructure Repository<Kabupaten, QueryParamFilters, Filter> kabupatenRepository) {
        return new KabupatenServiceBasic(kabupatenRepository);
    }
    
}
