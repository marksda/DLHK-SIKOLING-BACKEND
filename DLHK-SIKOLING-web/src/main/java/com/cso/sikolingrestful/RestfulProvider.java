package com.cso.sikolingrestful;

import com.cso.sikoling.abstraction.entity.alamat.Desa;
import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.person.JenisKelamin;
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
import com.cso.sikoling.abstraction.entity.alamat.Kecamatan;
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.Dokumen;
import com.cso.sikoling.abstraction.entity.dokumen.KategoriKbli;
import com.cso.sikoling.abstraction.entity.dokumen.Kbli;
import com.cso.sikoling.abstraction.entity.dokumen.VersiKbli;
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPengurusPermohonan;
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPermohonan;
import com.cso.sikoling.abstraction.entity.permohonan.PosisiTahapPemberkasan;
import com.cso.sikoling.abstraction.entity.permohonan.StatusFlowPermohonan;
import com.cso.sikoling.abstraction.entity.person.Person;
import com.cso.sikoling.abstraction.entity.perusahaan.Jabatan;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriModelPerizinan;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriPelakuUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.Pegawai;
import com.cso.sikoling.abstraction.entity.perusahaan.PelakuUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.Perusahaan;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
import com.cso.sikoling.abstraction.entity.security.HakAkses;
import com.cso.sikoling.abstraction.entity.security.oauth2.HashingPasswordType;
import com.cso.sikoling.abstraction.entity.security.oauth2.Jwa;
import com.cso.sikoling.abstraction.entity.security.oauth2.JwaType;
import com.cso.sikoling.abstraction.entity.security.oauth2.User;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import com.cso.sikoling.abstraction.entity.security.oauth2.Realm;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.repository.LocalStorageRepository;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.abstraction.service.alamat.PropinsiServiceBasic;
import com.cso.sikoling.main.Infrastructure;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.inject.Produces;
import com.cso.sikoling.abstraction.service.alamat.DesaServiceBasic;
import com.cso.sikoling.abstraction.service.alamat.KabupatenServiceBasic;
import com.cso.sikoling.abstraction.service.alamat.KecamatanServiceBasic;
import com.cso.sikoling.abstraction.service.dokumen.DokumenServiceBasic;
import com.cso.sikoling.abstraction.service.permohonan.KategoriPengurusPermohonanServiceBasic;
import com.cso.sikoling.abstraction.service.permohonan.KategoriPermohonanServiceBasic;
import com.cso.sikoling.abstraction.service.person.JenisKelaminServiceBasic;
import com.cso.sikoling.abstraction.service.person.PersonServiceBasic;
import com.cso.sikoling.abstraction.service.perusahaan.JabatanServiceBasic;
import com.cso.sikoling.abstraction.service.perusahaan.KategoriModelPerizinanServiceBasic;
import com.cso.sikoling.abstraction.service.perusahaan.KategoriSkalaUsahaServiceBasic;
import com.cso.sikoling.abstraction.service.perusahaan.KategoriPelakuUsahaServiceBasic;
import com.cso.sikoling.abstraction.service.perusahaan.PegawaiUsahaServiceBasic;
import com.cso.sikoling.abstraction.service.perusahaan.PelakuUsahaServiceBasic;
import com.cso.sikoling.abstraction.service.perusahaan.PerusahaanServiceBasic;
import com.cso.sikoling.abstraction.service.security.OtorisasiServiceBasic;
import com.cso.sikoling.abstraction.service.security.HakAksesServiceBasic;
import com.cso.sikoling.abstraction.service.security.oauth2.KeyServiceBasic;
import com.cso.sikoling.abstraction.service.security.oauth2.RealmServiceBasic;
import com.cso.sikoling.abstraction.service.security.oauth2.TokenServiceBasic;
import com.cso.sikoling.abstraction.service.KeyService;
import com.cso.sikoling.abstraction.service.LocalStorageService;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikoling.abstraction.service.UserService;
import com.cso.sikoling.abstraction.service.TokenService;
import com.cso.sikoling.abstraction.service.dokumen.KategoriKbliServiceBasic;
import com.cso.sikoling.abstraction.service.dokumen.KbliServiceBasic;
import com.cso.sikoling.abstraction.service.dokumen.VersiKbliServiceBasic;
import com.cso.sikoling.abstraction.service.permohonan.PosisiTahapPemberkasanServiceBasic;
import com.cso.sikoling.abstraction.service.permohonan.StatusFlowPermohonanServiceBasic;
import com.cso.sikoling.abstraction.service.security.oauth2.HashingPasswordTypeServiceBasic;
import com.cso.sikoling.abstraction.service.security.oauth2.JwaServiceBasic;
import com.cso.sikoling.abstraction.service.security.oauth2.JwaTypeServiceBasic;
import com.cso.sikoling.abstraction.service.security.oauth2.UserServiceBasic;
import com.cso.sikoling.abstraction.service.storage.LocalStorageServiceBasic;

@Stateless
@LocalBean
public class RestfulProvider {

    @Produces
    public Service<Propinsi> getPropinsiService(
            @Infrastructure Repository<Propinsi, QueryParamFilters, Filter> propinsiRepository) {
        return new PropinsiServiceBasic(propinsiRepository);
    }
    
    @Produces
    public Service<Kabupaten> getKabupatenService(
            @Infrastructure Repository<Kabupaten, QueryParamFilters, Filter> kabupatenRepository) {
        return new KabupatenServiceBasic(kabupatenRepository);
    }
    
    @Produces
    public Service<Kecamatan> getKecamatanService(
            @Infrastructure Repository<Kecamatan, QueryParamFilters, Filter> kecamatanRepository) {
        return new KecamatanServiceBasic(kecamatanRepository);
    }
    
    @Produces
    public Service<Desa> getDesaService(
            @Infrastructure Repository<Desa, QueryParamFilters, Filter> desaRepository) {
        return new DesaServiceBasic(desaRepository);
    }
    
    @Produces
    public Service<JenisKelamin> getJenisKelaminService(
            @Infrastructure Repository<JenisKelamin, QueryParamFilters, Filter> jenisKelaminRepository) {
        return new JenisKelaminServiceBasic(jenisKelaminRepository);
    }
    
    @Produces
    public Service<Person> getPersonService(
            @Infrastructure Repository<Person, QueryParamFilters, Filter> personRepository) {
        return new PersonServiceBasic(personRepository);
    }
    
    @Produces
    public Service<KategoriSkalaUsaha> getKategoriSkalaUsahaService(
            @Infrastructure Repository<KategoriSkalaUsaha, QueryParamFilters, Filter> kategoriSkalaUsahaRepository) {
        return new KategoriSkalaUsahaServiceBasic(kategoriSkalaUsahaRepository);
    }
    
    @Produces
    public Service<KategoriPelakuUsaha> getKategoriPelakuUsahaService(
            @Infrastructure Repository<KategoriPelakuUsaha, QueryParamFilters, Filter> kategoriPelakuUsahaRepository) {
        return new KategoriPelakuUsahaServiceBasic(kategoriPelakuUsahaRepository);
    }
    
    @Produces
    public Service<KategoriModelPerizinan> getKategoriModelPerizinanService(
            @Infrastructure Repository<KategoriModelPerizinan, QueryParamFilters, Filter> kategoriModelPerizinanRepository) {
        return new KategoriModelPerizinanServiceBasic(kategoriModelPerizinanRepository);
    }
    
    @Produces
    public Service<PelakuUsaha> getPelakuUsahaService(
            @Infrastructure Repository<PelakuUsaha, QueryParamFilters, Filter> pelakuUsahaRepository) {
        return new PelakuUsahaServiceBasic(pelakuUsahaRepository);
    }
    
    @Produces
    public Service<HakAkses> getHakAksesService(
            @Infrastructure Repository<HakAkses, QueryParamFilters, Filter> hakAksesRepository) {
        return new HakAksesServiceBasic(hakAksesRepository);
    }
    
    @Produces
    public Service<StatusFlowPermohonan> getStatusFlowPermohonanService(
            @Infrastructure Repository<StatusFlowPermohonan, QueryParamFilters, Filter> statusFlowPermohonanRepository) {
        return new StatusFlowPermohonanServiceBasic(statusFlowPermohonanRepository);
    }
    
    @Produces
    public Service<PosisiTahapPemberkasan> getPosisiTahapPemberkasanService(
            @Infrastructure Repository<PosisiTahapPemberkasan, QueryParamFilters, Filter> posisiTahapPemberkasanRepository) {
        return new PosisiTahapPemberkasanServiceBasic(posisiTahapPemberkasanRepository);
    }
    
    @Produces
    public Service<Otorisasi> getAutorisasiService(
            @Infrastructure Repository<Otorisasi, QueryParamFilters, Filter> autorisasiRepository) {
        return new OtorisasiServiceBasic(autorisasiRepository);
    }
    
    @Produces
    public Service<Perusahaan> getPerusahaanService(
            @Infrastructure Repository<Perusahaan, QueryParamFilters, Filter> perusahaanRepository) {
        return new PerusahaanServiceBasic(perusahaanRepository);
    }
    
    @Produces
    public Service<KategoriPermohonan> getKategoriPermohonanService(
            @Infrastructure Repository<KategoriPermohonan, QueryParamFilters, Filter> kategoriPermohonanRepository) {
        return new KategoriPermohonanServiceBasic(kategoriPermohonanRepository);
    }
    
    @Produces
    public Service<KategoriPengurusPermohonan> getKategoriPengurusPermohonanService(
            @Infrastructure Repository<KategoriPengurusPermohonan, QueryParamFilters, Filter> kategoriPengurusPermohonanRepository) {
        return new KategoriPengurusPermohonanServiceBasic(kategoriPengurusPermohonanRepository);
    }
    
    @Produces
    public Service<Jabatan> getJabatanService(
            @Infrastructure Repository<Jabatan, QueryParamFilters, Filter> jabatanRepository) {
        return new JabatanServiceBasic(jabatanRepository);
    }
    
    @Produces
    public Service<Pegawai> getPegawaiService(
            @Infrastructure Repository<Pegawai, QueryParamFilters, Filter> pegawaiRepository) {
        return new PegawaiUsahaServiceBasic(pegawaiRepository);
    }
    
    @Produces
    public Service<Dokumen> getDokumenService(
            @Infrastructure Repository<Dokumen, QueryParamFilters, Filter> dokumenRepository) {
        return new DokumenServiceBasic(dokumenRepository);
    }
    
    @Produces
    public Service<VersiKbli> getVersiKbliService(
            @Infrastructure Repository<VersiKbli, QueryParamFilters, Filter> versiKbliRepository) {
        return new VersiKbliServiceBasic(versiKbliRepository);
    }
    
    @Produces
    public Service<KategoriKbli> getKategoriKbliService(
            @Infrastructure Repository<KategoriKbli, QueryParamFilters, Filter> kategoriKbliRepository) {
        return new KategoriKbliServiceBasic(kategoriKbliRepository);
    }
    
    @Produces
    public Service<Kbli> getKbliService(
            @Infrastructure Repository<Kbli, QueryParamFilters, Filter> kbliRepository) {
        return new KbliServiceBasic(kbliRepository);
    }
    
    @Produces
    public TokenService<Token> getTokenService(
            @Infrastructure Repository<Token, QueryParamFilters, Filter> tokenRepository,
            @Infrastructure Repository<Key, QueryParamFilters, Filter> keyRepository,
            @Infrastructure Repository<HakAkses, QueryParamFilters, Filter> hakAksesRepository) {
        return new TokenServiceBasic(tokenRepository, keyRepository);
    }
    
    @Produces
    public Service<Realm> getRealmService(
            @Infrastructure Repository<Realm, QueryParamFilters, Filter> realmRepository) {
        return new RealmServiceBasic(realmRepository);
    }
    
    @Produces
    public KeyService<Key> getKeyService(
            @Infrastructure Repository<Key, QueryParamFilters, Filter> keyRepository) {
        return new KeyServiceBasic(keyRepository);
    }
    
    @Produces
    public UserService<User> getUserService(
            @Infrastructure Repository<User, QueryParamFilters, Filter> userRepository) {
        return new UserServiceBasic(userRepository);
    }
    
    @Produces
    public Service<JwaType> getJwaTypeService(
            @Infrastructure Repository<JwaType, QueryParamFilters, Filter> jwaTypeRepository) {
        return new JwaTypeServiceBasic(jwaTypeRepository);
    }
    
    @Produces
    public Service<Jwa> getJwaService(
            @Infrastructure Repository<Jwa, QueryParamFilters, Filter> jwaRepository) {
        return new JwaServiceBasic(jwaRepository);
    }
    
    @Produces
    public Service<HashingPasswordType> getHashingPasswordTypeService(
            @Infrastructure Repository<HashingPasswordType, QueryParamFilters, Filter> hashingPasswordTypeRepository) {
        return new HashingPasswordTypeServiceBasic(hashingPasswordTypeRepository);
    }
    
    @Produces
    public LocalStorageService getLocalStorageService(@Infrastructure LocalStorageRepository localStorageRepository) {
        return new LocalStorageServiceBasic(localStorageRepository);
    }
    
}
