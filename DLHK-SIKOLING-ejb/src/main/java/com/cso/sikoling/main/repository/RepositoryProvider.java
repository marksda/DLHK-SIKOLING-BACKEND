package com.cso.sikoling.main.repository;

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
}
