package com.cso.sikoling.main.repository;

import com.cso.sikoling.main.repository.alamat.DesaRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.KabupatenRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.KecamatanRepositoryJPA;
import com.cso.sikoling.main.repository.alamat.PropinsiRepositoryJPA;
import com.cso.sikoling.main.repository.dokumen.MasterDokumenRepositoryJPA;
import com.cso.sikoling.main.repository.dokumen.KategoriKbliRepositoryJPA;
import com.cso.sikoling.main.repository.dokumen.KbliRepositoryJPA;
import com.cso.sikoling.main.repository.dokumen.RegisterDokumenRepositoryJPA;
import com.cso.sikoling.main.repository.dokumen.RegisterDokumenSementaraRepositoryJPA;
import com.cso.sikoling.main.repository.dokumen.StatusDokumenRepositoryJPA;
import com.cso.sikoling.main.repository.dokumen.VersiKbliRepositoryJPA;
import com.cso.sikoling.main.repository.permohonan.KategoriPengurusPermohonanRepositoryJPA;
import com.cso.sikoling.main.repository.permohonan.KategoriPermohonanRepositoryJPA;
import com.cso.sikoling.main.repository.permohonan.PosisiTahapPemberkasanRepositoryJPA;
import com.cso.sikoling.main.repository.permohonan.StatusFlowPermohonanRepositoryJPA;
import com.cso.sikoling.main.repository.permohonan.StatusPermohonanRepositoryJPA;
import com.cso.sikoling.main.repository.person.JenisKelaminRepositoryJPA;
import com.cso.sikoling.main.repository.person.PersonRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.JabatanRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.KategoriModelPerizinanRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.KategoriPelakuUsahaRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.KategoriSkalaUsahaRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.PegawaiRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.PelakuUsahaRepositoryJPA;
import com.cso.sikoling.main.repository.perusahaan.PerusahaanRepositoryJPA;
import com.cso.sikoling.main.repository.security.OtorisasiRepositoryJPA;
import com.cso.sikoling.main.repository.security.HakAksesRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.HashingPasswordTypeRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.UserRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.JwaRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.JwaTypeRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.KeyRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.RealmRepositoryJPA;
import com.cso.sikoling.main.repository.security.oauth2.TokenRepositoryJPA;
import com.cso.sikoling.main.repository.storage.LocalStorageImpl;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Properties;

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
    public PosisiTahapPemberkasanRepositoryJPA getPosisiTahapPemberkasanRepositoryJPA(EntityManager entityManager) {
        return new PosisiTahapPemberkasanRepositoryJPA(entityManager);
    }
    
    @Produces
    public OtorisasiRepositoryJPA getAutorisasiRepositoryJPA(EntityManager entityManager) {
        return new OtorisasiRepositoryJPA(entityManager);
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
    public StatusFlowPermohonanRepositoryJPA getStatusFlowPermohonanRepositoryJPA(EntityManager entityManager) {
        return new StatusFlowPermohonanRepositoryJPA(entityManager);
    }
    
    @Produces
    public StatusPermohonanRepositoryJPA getStatusPermohonanRepositoryJPA(EntityManager entityManager) {
        return new StatusPermohonanRepositoryJPA(entityManager);
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
    public MasterDokumenRepositoryJPA getDokumenRepositoryJPA(EntityManager entityManager) {
        return new MasterDokumenRepositoryJPA(entityManager);
    }
    
    @Produces
    public VersiKbliRepositoryJPA getVersiKbliRepositoryJPA(EntityManager entityManager) {
        return new VersiKbliRepositoryJPA(entityManager);
    }
    
    @Produces
    public KategoriKbliRepositoryJPA getKategoriKbliRepositoryJPA(EntityManager entityManager) {
        return new KategoriKbliRepositoryJPA(entityManager);
    }
    
    @Produces
    public StatusDokumenRepositoryJPA getStatusDokumenRepositoryJPA(EntityManager entityManager) {
        return new StatusDokumenRepositoryJPA(entityManager);
    }
    
    @Produces
    public RegisterDokumenRepositoryJPA getRegisterDokumenRepositoryJPA(EntityManager entityManager) {
        return new RegisterDokumenRepositoryJPA(entityManager);
    }
    
    @Produces
    public RegisterDokumenSementaraRepositoryJPA getRegisterDokumenSementaraRepositoryJPA(EntityManager entityManager) {
        return new RegisterDokumenSementaraRepositoryJPA(entityManager);
    }
    
    @Produces
    public KbliRepositoryJPA getKbliRepositoryJPA(EntityManager entityManager) {
        return new KbliRepositoryJPA(entityManager);
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
    
    @Produces
    public LocalStorageImpl getLocalStorageImpl(Properties properties) {
        return new LocalStorageImpl(properties.getProperty("STORAGE_PATH"));
    }
    
}