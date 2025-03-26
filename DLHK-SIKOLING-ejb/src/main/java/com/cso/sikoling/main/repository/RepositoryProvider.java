package com.cso.sikoling.main.repository;

import com.cso.sikoling.main.repository.alamat.DesaRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.KabupatenRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.KecamatanRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.PropinsiRepositoryJPA;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
@LocalBean
public class RepositoryProvider {

    @PersistenceContext(unitName = "dlhk")
    private EntityManager em;
    
    @Produces
    public EntityManager getDlhkDatabaseEntityManager() {
        return em;
    }
    
    @Produces
    public PropinsiRepositoryJPA getPropinsiRepositoryJPA(EntityManager entityManager) {
        return new PropinsiRepositoryJPA(entityManager);
    }
    
    @Produces
    public KabupatenRepositoryJPA getKabupatenRepositoryJPA(EntityManager entityManager) {
        return new KabupatenRepositoryJPA(entityManager);
    }
    
    @Produces
    public KecamatanRepositoryJPA getKecamatanRepositoryJPA(EntityManager entityManager) {
        return new KecamatanRepositoryJPA(entityManager);
    }
    
    @Produces
    public DesaRepositoryJPA getDesaRepositoryJPA(EntityManager entityManager) {
        return new DesaRepositoryJPA(entityManager);
    }
    
}