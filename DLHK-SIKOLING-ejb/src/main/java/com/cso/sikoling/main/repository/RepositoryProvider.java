package com.cso.sikoling.main.repository;

import com.cso.sikoling.main.repository.alamat.DesaRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.KabupatenRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.KecamatanRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.PropinsiRepositoryJPA;
import com.cso.sikoling.main.repository.dokumen.DokumenRepositoryJPA;
import com.cso.sikoling.main.repository.permohonan.KategoriPengurusPermohonanRepositoryJPA;
import com.cso.sikoling.main.repository.permohonan.KategoriPermohonanRepositoryJPA;
import com.cso.sikoling.main.repository.person.JenisKelaminRepositoryJPA;
import com.cso.sikoling.main.repository.person.PersonRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.JabatanRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.KategoriModelPerizinanRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.KategoriPelakuUsahaRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.KategoriSkalaUsahaRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.PegawaiRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.PelakuUsahaRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.PerusahaanRepositoryJPA;
import com.cso.sikoling.main.repository.security.AutorisasiRepositoryJPA;
import com.cso.sikoling.main.repository.security.HakAksesRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.HashingPasswordTypeRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.UserRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.JwaRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.JwaTypeRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.KeyRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.RealmRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.TokenRepositoryJPA;
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
    
    @Produces
    public KategoriPermohonanRepositoryJPA getKategoriPermohonanRepositoryJPA(EntityManager entityManager) {
        return new KategoriPermohonanRepositoryJPA(entityManager);
    }
    
    @Produces
    public KategoriPengurusPermohonanRepositoryJPA getKategoriPengurusPermohonanRepositoryJPA(EntityManager entityManager) {
        return new KategoriPengurusPermohonanRepositoryJPA(entityManager);
    }
    
    @Produces
    public JabatanRepositoryJPA getJabatanRepositoryJPA(EntityManager entityManager) {
        return new JabatanRepositoryJPA(entityManager);
    }
    
    @Produces
    public PegawaiRepositoryJPA getPegawaiRepositoryJPA(EntityManager entityManager) {
        return new PegawaiRepositoryJPA(entityManager);
    }
    
    @Produces
    public DokumenRepositoryJPA getDokumenRepositoryJPA(EntityManager entityManager) {
        return new DokumenRepositoryJPA(entityManager);
    }
    
    @Produces
    public TokenRepositoryJPA getTokenRepositoryJPA(EntityManager entityManager) {
        return new TokenRepositoryJPA(entityManager);
    }
    
    @Produces
    public JwaTypeRepositoryJPA getJwaTypeRepositoryJPA(EntityManager entityManager) {
        return new JwaTypeRepositoryJPA(entityManager);
    }
    
    @Produces
    public JwaRepositoryJPA getJwaRepositoryJPA(EntityManager entityManager) {
        return new JwaRepositoryJPA(entityManager);
    }
    
    @Produces
    public RealmRepositoryJPA getRealmRepositoryJPA(EntityManager entityManager) {
        return new RealmRepositoryJPA(entityManager);
    }
    
    @Produces
    public KeyRepositoryJPA getKeyRepositoryJPA(EntityManager entityManager) {
        return new KeyRepositoryJPA(entityManager);
    }
    
    @Produces
    public UserRepositoryJPA getUserRepositoryJPA(EntityManager entityManager) {
        return new UserRepositoryJPA(entityManager);
    }
    
    @Produces
    public HashingPasswordTypeRepositoryJPA getHashingPasswordTypeRepositoryJPA(EntityManager entityManager) {
        return new HashingPasswordTypeRepositoryJPA(entityManager);
    }
    
}