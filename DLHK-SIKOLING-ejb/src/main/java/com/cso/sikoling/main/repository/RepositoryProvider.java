package com.cso.sikoling.main.repository;

import com.cso.sikoling.main.repository.alamat.DesaRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.KabupatenRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.KecamatanRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.PropinsiRepositoryJPA;
import com.cso.sikoling.main.repository.person.JenisKelaminRepositoryJPA;
import com.cso.sikoling.main.repository.person.PersonRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.KategoriModelPerizinanRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.KategoriPelakuUsahaRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.KategoriSkalaUsahaRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.PelakuUsahaRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.PerusahaanRepositoryJPA;
import com.cso.sikoling.main.repository.security.AutorisasiRepositoryJPA;
import com.cso.sikoling.main.repository.security.HakAksesRepositoryJPA;
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
    
    @Produces
    public JenisKelaminRepositoryJPA getJenisKelaminRepositoryJPA(EntityManager entityManager) {
        return new JenisKelaminRepositoryJPA(entityManager);
    }
    
    @Produces
    public PersonRepositoryJPA getPersonRepositoryJPA(EntityManager entityManager) {
        return new PersonRepositoryJPA(entityManager);
    }
    
    @Produces
    public KategoriSkalaUsahaRepositoryJPA getKategoriSkalaUsahaRepositoryJPA(EntityManager entityManager) {
        return new KategoriSkalaUsahaRepositoryJPA(entityManager);
    }
    
    @Produces
    public KategoriPelakuUsahaRepositoryJPA getKategoriPelakuUsahaRepositoryJPA(EntityManager entityManager) {
        return new KategoriPelakuUsahaRepositoryJPA(entityManager);
    }
    
    @Produces
    public KategoriModelPerizinanRepositoryJPA getKategoriModelPerizinanRepositoryJPA(EntityManager entityManager) {
        return new KategoriModelPerizinanRepositoryJPA(entityManager);
    }
    
    @Produces
    public PelakuUsahaRepositoryJPA getPelakuUsahaRepositoryJPA(EntityManager entityManager) {
        return new PelakuUsahaRepositoryJPA(entityManager);
    }
    
    @Produces
    public HakAksesRepositoryJPA getHakAksesRepositoryJPA(EntityManager entityManager) {
        return new HakAksesRepositoryJPA(entityManager);
    }
    
    @Produces
    public AutorisasiRepositoryJPA getAutorisasiRepositoryJPA(EntityManager entityManager) {
        return new AutorisasiRepositoryJPA(entityManager);
    }
    
    @Produces
    public PerusahaanRepositoryJPA getPerusahaanRepositoryJPA(EntityManager entityManager) {
        return new PerusahaanRepositoryJPA(entityManager);
    }
    
}