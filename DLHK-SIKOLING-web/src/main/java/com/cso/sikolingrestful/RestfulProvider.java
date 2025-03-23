package com.cso.sikolingrestful;

import com.cso.sikoling.abstraction.repository.PropinsiRepository;
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
            @Infrastructure PropinsiRepository propinsiRepository) {
        return new PropinsiServiceBasic(propinsiRepository);
    }
}
