package com.cso.sikolingrestful;

import com.cso.sikoling.abstraction.entity.alamat.Desa;
import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.person.JenisKelamin;
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
import com.cso.sikoling.abstraction.entity.alamat.Kecamatan;
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.Dokumen;
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPengurusPermohonan;
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPermohonan;
import com.cso.sikoling.abstraction.entity.person.Person;
import com.cso.sikoling.abstraction.entity.perusahaan.Jabatan;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriModelPerizinan;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriPelakuUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.Pegawai;
import com.cso.sikoling.abstraction.entity.perusahaan.PelakuUsaha;
import com.cso.sikoling.abstraction.entity.perusahaan.Perusahaan;
import com.cso.sikoling.abstraction.entity.security.Autorisasi;
import com.cso.sikoling.abstraction.entity.security.HakAkses;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.repository.Repository;
import com.cso.sikoling.abstraction.repository.RepositoryToken;
import com.cso.sikoling.abstraction.service.alamat.PropinsiServiceBasic;
import com.cso.sikoling.main.Infrastructure;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.enterprise.inject.Produces;
import com.cso.sikoling.abstraction.service.alamat.DesaServiceBasic;
import com.cso.sikoling.abstraction.service.alamat.KabupatenServiceBasic;
import com.cso.sikoling.abstraction.service.alamat.KecamatanServiceBasic;
import com.cso.sikoling.abstraction.service.DAOService;
import com.cso.sikoling.abstraction.service.DAOTokenService;
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
import com.cso.sikoling.abstraction.service.security.AutorisasiServiceBasic;
import com.cso.sikoling.abstraction.service.security.HakAksesServiceBasic;
import com.cso.sikoling.abstraction.service.security.oauth2.TokenServiceBasic;

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
    
    @Produces
    public DAOService<Person> getPersonService(
            @Infrastructure Repository<Person, QueryParamFilters, Filter> personRepository) {
        return new PersonServiceBasic(personRepository);
    }
    
    @Produces
    public DAOService<KategoriSkalaUsaha> getKategoriSkalaUsahaService(
            @Infrastructure Repository<KategoriSkalaUsaha, QueryParamFilters, Filter> kategoriSkalaUsahaRepository) {
        return new KategoriSkalaUsahaServiceBasic(kategoriSkalaUsahaRepository);
    }
    
    @Produces
    public DAOService<KategoriPelakuUsaha> getKategoriPelakuUsahaService(
            @Infrastructure Repository<KategoriPelakuUsaha, QueryParamFilters, Filter> kategoriPelakuUsahaRepository) {
        return new KategoriPelakuUsahaServiceBasic(kategoriPelakuUsahaRepository);
    }
    
    @Produces
    public DAOService<KategoriModelPerizinan> getKategoriModelPerizinanService(
            @Infrastructure Repository<KategoriModelPerizinan, QueryParamFilters, Filter> kategoriModelPerizinanRepository) {
        return new KategoriModelPerizinanServiceBasic(kategoriModelPerizinanRepository);
    }
    
    @Produces
    public DAOService<PelakuUsaha> getPelakuUsahaService(
            @Infrastructure Repository<PelakuUsaha, QueryParamFilters, Filter> pelakuUsahaRepository) {
        return new PelakuUsahaServiceBasic(pelakuUsahaRepository);
    }
    
    @Produces
    public DAOService<HakAkses> getHakAksesService(
            @Infrastructure Repository<HakAkses, QueryParamFilters, Filter> hakAksesRepository) {
        return new HakAksesServiceBasic(hakAksesRepository);
    }
    
    @Produces
    public DAOService<Autorisasi> getAutorisasiService(
            @Infrastructure Repository<Autorisasi, QueryParamFilters, Filter> autorisasiRepository) {
        return new AutorisasiServiceBasic(autorisasiRepository);
    }
    
    @Produces
    public DAOService<Perusahaan> getPerusahaanService(
            @Infrastructure Repository<Perusahaan, QueryParamFilters, Filter> perusahaanRepository) {
        return new PerusahaanServiceBasic(perusahaanRepository);
    }
    
    @Produces
    public DAOService<KategoriPermohonan> getKategoriPermohonanService(
            @Infrastructure Repository<KategoriPermohonan, QueryParamFilters, Filter> kategoriPermohonanRepository) {
        return new KategoriPermohonanServiceBasic(kategoriPermohonanRepository);
    }
    
    @Produces
    public DAOService<KategoriPengurusPermohonan> getKategoriPengurusPermohonanService(
            @Infrastructure Repository<KategoriPengurusPermohonan, QueryParamFilters, Filter> kategoriPengurusPermohonanRepository) {
        return new KategoriPengurusPermohonanServiceBasic(kategoriPengurusPermohonanRepository);
    }
    
    @Produces
    public DAOService<Jabatan> getJabatanService(
            @Infrastructure Repository<Jabatan, QueryParamFilters, Filter> jabatanRepository) {
        return new JabatanServiceBasic(jabatanRepository);
    }
    
    @Produces
    public DAOService<Pegawai> getPegawaiService(
            @Infrastructure Repository<Pegawai, QueryParamFilters, Filter> pegawaiRepository) {
        return new PegawaiUsahaServiceBasic(pegawaiRepository);
    }
    
    @Produces
    public DAOService<Dokumen> getDokumenService(
            @Infrastructure Repository<Dokumen, QueryParamFilters, Filter> dokumenRepository) {
        return new DokumenServiceBasic(dokumenRepository);
    }
    
    @Produces
    public DAOTokenService<Token> getTokenService(
            @Infrastructure RepositoryToken<Token, QueryParamFilters, Filter> tokenRepository) {
        return new TokenServiceBasic(tokenRepository);
    }
}
