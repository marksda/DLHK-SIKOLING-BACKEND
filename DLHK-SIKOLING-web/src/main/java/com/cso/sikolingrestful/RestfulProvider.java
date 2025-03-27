package com.cso.sikolingrestful;

import com.cso.sikoling.abstraction.entity.alamat.Desa;
import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.person.JenisKelamin;
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
import com.cso.sikoling.abstraction.entity.alamat.Kecamatan;
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.abstraction.service.alamat.PropinsiServiceBasic;
import com.cso.sikoling.main.Infrastructure;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.inject.Produces;
import com.cso.sikoling.abstraction.service.alamat.DesaServiceBasic;
import com.cso.sikoling.abstraction.service.alamat.KabupatenServiceBasic;
import com.cso.sikoling.abstraction.service.alamat.KecamatanServiceBasic;
import com.cso.sikoling.abstraction.service.DAOService;
import com.cso.sikoling.abstraction.service.person.JenisKelaminServiceBasic;

@Stateless
@LocalBean
public class RestfulProvider {

    @Produces
    public DAOService<Propinsi> getPropinsiService(
            @Infrastructure Repository<Propinsi, QueryParamFilters, Filter> propinsiRepository) {
        return new PropinsiServiceBasic(propinsiRepository);
    }
    
    @Produces
    public DAOService<Kabupaten> getKabupatenService(
            @Infrastructure Repository<Kabupaten, QueryParamFilters, Filter> kabupatenRepository) {
        return new KabupatenServiceBasic(kabupatenRepository);
    }
    
    @Produces
    public DAOService<Kecamatan> getKecamatanService(
            @Infrastructure Repository<Kecamatan, QueryParamFilters, Filter> kecamatanRepository) {
        return new KecamatanServiceBasic(kecamatanRepository);
    }
    
    @Produces
    public DAOService<Desa> getDesaService(
            @Infrastructure Repository<Desa, QueryParamFilters, Filter> desaRepository) {
        return new DesaServiceBasic(desaRepository);
    }
    
    @Produces
    public DAOService<JenisKelamin> getJenisKelaminService(
            @Infrastructure Repository<JenisKelamin, QueryParamFilters, Filter> jenisKelaminRepository) {
        return new JenisKelaminServiceBasic(jenisKelaminRepository);
    }
    
}
