package com.cso.sikolingrestful.resources.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumen;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumenSementara;
import com.cso.sikoling.abstraction.entity.perusahaan.Pegawai;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
import com.cso.sikoling.abstraction.service.LocalStorageService;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikolingrestful.resources.QueryParamFiltersDTO;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.json.Json;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import static com.cso.sikolingrestful.Role.ADMINISTRATOR;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.exception.UnspecifiedException;
import com.cso.sikolingrestful.resources.FilterDTO;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Stateless
@LocalBean
@Path("register_dokumen")
public class RegisterDokumenResource {
    
    @Inject
    private Service<RegisterDokumen> registerDokumenService;
    
    @Inject
    private Service<RegisterDokumenSementara> registerDokumenSementaraService;
    
    @Inject
    private LocalStorageService localStorageService;
    
    @Inject
    private Service<Pegawai> pegawaiService;
    
    @GET
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR, Role.UMUM})
    @Produces({MediaType.APPLICATION_JSON})
    public List<RegisterDokumenDTO> getDaftarData(
            @Context ContainerRequestContext crc,
            @QueryParam("filters") String queryParamsStr) throws UnspecifiedException {
        
        List<RegisterDokumen> daftarRegisterDokumen;
        Role role = (Role) crc.getProperty("role");
        Jsonb jsonb = JsonbBuilder.create();
        QueryParamFilters queryParamFilters;
        QueryParamFiltersDTO queryParamFiltersDTO = null;
        
        if(queryParamsStr != null) {
            queryParamFiltersDTO = jsonb.fromJson(
                        queryParamsStr, QueryParamFiltersDTO.class);
        }
        
        switch (role) {
            case ADMINISTRATOR -> {
                queryParamFilters = queryParamFiltersDTO != null ?
                        queryParamFiltersDTO.toQueryParamFilters() : null;
                daftarRegisterDokumen = registerDokumenService
                                            .getDaftarData(queryParamFilters);
            }
            case UMUM -> {                
                queryParamFilters = queryParamFiltersDTO != null ?
                        queryParamFiltersDTO.toQueryParamFilters() : null;
                daftarRegisterDokumen = registerDokumenService
                                            .getDaftarData(queryParamFilters);
            }
            default -> {                
                throw new UnspecifiedException(401, "Akses ditolak");
            }
        }
        
        if(daftarRegisterDokumen == null) {
            return new ArrayList<>();
        }
        
        return daftarRegisterDokumen
                            .stream()
                            .map(t -> new RegisterDokumenDTO(t))
                            .collect(Collectors.toList());
        
    }
    
    @POST
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR, Role.UMUM})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RegisterDokumenDTO save(
            @Context ContainerRequestContext crc,
            RegisterDokumenDTO registerDokumenDTO) throws SQLException, UnspecifiedException {  
        
        Otorisasi otorisasi = (Otorisasi) crc.getProperty("otoritas");
        Role role = (Role) crc.getProperty("role");
        Filter filter;
        List<Filter> fields_filter = new ArrayList<>();
        QueryParamFilters qFilter;
        RegisterDokumenSementara regDokSementara;
        JsonObject metaFile;
        JsonObjectBuilder builder;
        List<RegisterDokumenSementara> daftarRegDokTmp;
        
        switch (role) {
            case ADMINISTRATOR -> {
                filter = new Filter("id", registerDokumenDTO.getId());
                fields_filter.add(filter);
                qFilter = new QueryParamFilters(false, null, fields_filter, null);
                daftarRegDokTmp = registerDokumenSementaraService.getDaftarData(qFilter);
                
                if(daftarRegDokTmp == null) {
                    throw new IllegalArgumentException(
                        "data register Dokumen tidak sesuai dengan file yang diupload"
                    );
                }
                
                regDokSementara = daftarRegDokTmp.getFirst();
                builder = Json.createObjectBuilder(regDokSementara.getMetaFile());
                
                if(registerDokumenDTO.getIs_validated()) {
                    builder.add("UserCanWrite", false);
                    metaFile = builder.build();
                }
                else {
                    metaFile = builder.build();
                }
            }
            case UMUM -> {
                filter = new Filter("id", registerDokumenDTO.getId());
                fields_filter.add(filter);
                filter = new Filter(
                    "id_perusahaan", registerDokumenDTO.getPerusahaan().getId());
                fields_filter.add(filter);
                qFilter = new QueryParamFilters(false, null,fields_filter, null);
                daftarRegDokTmp = registerDokumenSementaraService.getDaftarData(qFilter);
                
                if(daftarRegDokTmp == null) {
                    throw new IllegalArgumentException(
                        "data register Dokumen tidak sesuai dengan file yang diupload"
                    );
                }
                
                regDokSementara = daftarRegDokTmp.getFirst();  
                metaFile = regDokSementara.getMetaFile();
                fields_filter.clear();
                filter = new Filter("id_person", otorisasi.getPerson().getId());
                fields_filter.add(filter);
                filter = new Filter("id_perusahaan", regDokSementara.getIdPerusahaan());
                fields_filter.add(filter);
                qFilter = new QueryParamFilters(false, null, fields_filter, null);
                List<Pegawai> daftarPegawai = pegawaiService.getDaftarData(qFilter);
                
                if(daftarPegawai == null) {
                    throw new IllegalArgumentException("Akses ditolak");
                }
            }
            default -> {                
                throw new UnspecifiedException(500, "Akses ditolak");
            } 
        }
        
        RegisterDokumen registerDokumen =  new RegisterDokumen(
                registerDokumenDTO.getId(), 
                registerDokumenDTO.getPerusahaan().toPerusahaan(), 
                registerDokumenDTO.getDokumen().toDokumen(), 
                null, 
                otorisasi, 
                registerDokumenDTO.getNama_file(), 
                registerDokumenDTO.getStatus_dokumen().toStatusDokumen(),
                null, 
                registerDokumenDTO.getIs_validated(), 
                metaFile, 
                null
            );
        
        registerDokumen = registerDokumenService.save(registerDokumen);
        
        if(registerDokumen != null) {
            registerDokumenSementaraService.delete(regDokSementara.getId());
            return new RegisterDokumenDTO(registerDokumen);      
        }
        else {
            try {
                String subPathLocation = File.separator
                        .concat(regDokSementara.getIdJenisDokumen());
                localStorageService.delete(regDokSementara.getNamaFile(), subPathLocation);
                registerDokumenSementaraService.delete(regDokSementara.getId());
                throw new IllegalArgumentException("file dokumen tidak bisa disimpan");
            } catch (IOException ex) {
                registerDokumenSementaraService.delete(regDokSementara.getId());
                throw new IllegalArgumentException("file dokumen tidak bisa disimpan");
            }
        }  
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR, Role.UMUM})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RegisterDokumenDTO update(
            @Context ContainerRequestContext crc,
            @PathParam("idLama") String idLama, 
            RegisterDokumenDTO regDokDTOBaru) throws SQLException, UnspecifiedException {
        
        Otorisasi otorisasi = (Otorisasi) crc.getProperty("otoritas");
        Role role = (Role) crc.getProperty("role");
        Filter filter;
        List<Filter> fields_filter = new ArrayList<>();
        QueryParamFilters qFilter;
        filter = new Filter("id", idLama);
        fields_filter.add(filter);
        qFilter = new QueryParamFilters(false, null,fields_filter, null);
        List<RegisterDokumen> daftarRegDok;
        
        daftarRegDok = registerDokumenService.getDaftarData(qFilter);
        
        if(daftarRegDok == null) {
            throw new IllegalArgumentException("Register dokumen tidak terdaftar dalam sistem");
        }
        
        RegisterDokumen regDokLama =  daftarRegDok.getFirst();        
        
        if(!(regDokLama.getMasterDokumen().getId().equals(regDokDTOBaru.getDokumen().getId())
                && regDokLama.getPerusahaan().getId().equals(regDokDTOBaru.getPerusahaan().getId()))) {
            throw new IllegalArgumentException("perusahaan dan jenis dokumen harus sama dengan data register dokumen lama");
        }
        
        JsonObject metaFile;   
        JsonObjectBuilder builder;
        RegisterDokumen registerDokumenBaru;
                                                            
        switch (role) {
            case ADMINISTRATOR -> {
                builder = Json.createObjectBuilder(regDokLama.getMetaFile());
                builder.add("UserCanWrite", !regDokDTOBaru.getIs_validated());
                metaFile = builder.build();
                registerDokumenBaru =  new RegisterDokumen(
                    regDokLama.getId(), 
                    regDokLama.getPerusahaan(), 
                    regDokLama.getMasterDokumen(), 
                    null, 
                    otorisasi, 
                    regDokLama.getNamaFile(), 
                    regDokDTOBaru.getStatus_dokumen().toStatusDokumen(),
                    null, 
                    regDokDTOBaru.getIs_validated(), 
                    metaFile, 
                    null
                );
            }
            case UMUM -> {
                fields_filter.clear();
                filter = new Filter("id_person", otorisasi.getPerson().getId());
                fields_filter.add(filter);
                filter = new Filter("id_perusahaan", regDokLama.getPerusahaan().getId());
                fields_filter.add(filter);
                qFilter = new QueryParamFilters(false, null, fields_filter, null);
                List<Pegawai> daftarPegawai = pegawaiService.getDaftarData(qFilter);
                
                if(daftarPegawai == null) {
                    throw new IllegalArgumentException("Akses ditolak");
                }
                
                metaFile = regDokLama.getMetaFile();
                registerDokumenBaru =  new RegisterDokumen(
                    regDokLama.getId(), 
                    regDokLama.getPerusahaan(), 
                    regDokLama.getMasterDokumen(), 
                    null, 
                    otorisasi, 
                    regDokLama.getNamaFile(), 
                    regDokDTOBaru.getStatus_dokumen().toStatusDokumen(),
                    null, 
                    regDokDTOBaru.getIs_validated(), 
                    metaFile, 
                    null
                );
            }
            default -> {
                throw new UnspecifiedException(500, "Akses ditolak");
            }
        }
        
        registerDokumenBaru = registerDokumenService.update(registerDokumenBaru);
        
        fields_filter.clear();
        filter.setField_name("id");
        filter.setValue(registerDokumenBaru.getId());
        fields_filter.add(filter);
        qFilter.setDaftarFieldFilter(fields_filter);
        
        List<RegisterDokumenSementara> daftarRegDokSementara = 
                registerDokumenSementaraService.getDaftarData(qFilter);
        
        RegisterDokumenSementara dokSementara = daftarRegDokSementara != null ?
                    daftarRegDokSementara.getFirst() : null;
        
        if(dokSementara != null) {
            try {
                String subPathLocationTujuan = File.separator
                        .concat(regDokLama.getMasterDokumen().getId())
                        .concat(File.separator)
                        .concat(regDokLama.getNamaFile());
                
                String subPathLocationAsal = File.separator
                        .concat(registerDokumenBaru.getMasterDokumen().getId())
                        .concat(File.separator)
                        .concat(registerDokumenBaru.getNamaFile());
                
                localStorageService.move(subPathLocationAsal, subPathLocationTujuan);
            } catch (IOException ex) {
                throw new IllegalArgumentException("gagal memindahkan file register dokuman");
            }
        }
        
        return new RegisterDokumenDTO(registerDokumenBaru);        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RegisterDokumenDTO updateId(
            @Context ContainerRequestContext crc,
            @PathParam("idLama") String idLama, 
            RegisterDokumenDTO regDokDTOBaru) throws SQLException, UnspecifiedException {
        
        Otorisasi otorisasi = (Otorisasi) crc.getProperty("otoritas");
        Role role = (Role) crc.getProperty("role");
        Filter filter;
        List<Filter> fields_filter = new ArrayList<>();
        QueryParamFilters qFilter;
        filter = new Filter("id", idLama);
        fields_filter.add(filter);
        qFilter = new QueryParamFilters(false, null, fields_filter, null);
        List<RegisterDokumen> daftarRegDok = registerDokumenService.getDaftarData(qFilter);
        
        if(daftarRegDok == null) {
             throw new IllegalArgumentException("Register dokumen tidak terdaftar dalam sistem");
        }
        
        RegisterDokumen regDokLama =  daftarRegDok.getFirst();        
        
        if(!(regDokLama.getMasterDokumen().getId().equals(regDokDTOBaru.getDokumen().getId())
                || regDokLama.getPerusahaan().getId().equals(regDokDTOBaru.getPerusahaan().getId()))) {
            throw new IllegalArgumentException("perusahaan atau jenis dokumen harus berbeda dengan data register dokumen lama");
        }
        
        JsonObject metaFile;   
        JsonObjectBuilder builder;
        RegisterDokumen registerDokumenBaru;
        
        switch (role) {
            case ADMINISTRATOR -> {
                builder = Json.createObjectBuilder(regDokLama.getMetaFile());
                builder.add("UserCanWrite", !regDokDTOBaru.getIs_validated());
                metaFile = builder.build();
                
                registerDokumenBaru =  new RegisterDokumen(
                    null, 
                    regDokDTOBaru.getPerusahaan().toPerusahaan(), 
                    regDokDTOBaru.getDokumen().toDokumen(), 
                    null, 
                    otorisasi, 
                    regDokDTOBaru.getNama_file(), 
                    regDokDTOBaru.getStatus_dokumen().toStatusDokumen(),
                    null, 
                    regDokDTOBaru.getIs_validated(), 
                    metaFile, 
                    null
                );
            }
            case UMUM -> {
                fields_filter.clear();
                filter = new Filter("id_person", otorisasi.getPerson().getId());
                fields_filter.add(filter);
                filter = new Filter("id_perusahaan", regDokLama.getPerusahaan().getId());
                fields_filter.add(filter);
                qFilter = new QueryParamFilters(false, null, fields_filter, null);
                List<Pegawai> daftarPegawai = pegawaiService.getDaftarData(qFilter);
                
                if(daftarPegawai == null) {
                    throw new IllegalArgumentException("Akses ditolak");
                }
                
                metaFile = regDokLama.getMetaFile();
                registerDokumenBaru =  new RegisterDokumen(
                    null, 
                    regDokDTOBaru.getPerusahaan().toPerusahaan(), 
                    regDokDTOBaru.getDokumen().toDokumen(), 
                    null, 
                    otorisasi, 
                    regDokDTOBaru.getNama_file(), 
                    regDokDTOBaru.getStatus_dokumen().toStatusDokumen(),
                    null, 
                    regDokDTOBaru.getIs_validated(), 
                    metaFile, 
                    null
                );
            }
            default -> {
                throw new UnspecifiedException(500, "Akses ditolak");
            }
        }
        
        registerDokumenBaru = registerDokumenService.update(registerDokumenBaru);
        
        fields_filter.clear();
        filter.setField_name("id");
        filter.setValue(registerDokumenBaru.getId());
        fields_filter.add(filter);
        qFilter.setDaftarFieldFilter(fields_filter);
        
        List<RegisterDokumenSementara> daftarRegDokSementara = 
                registerDokumenSementaraService.getDaftarData(qFilter);
        
        RegisterDokumenSementara dokSementara = daftarRegDokSementara != null ?
                    daftarRegDokSementara.getFirst() : null;
        
        if(dokSementara ==  null) {
            try {
                String subPathLocationAsal = File.separator
                        .concat(regDokLama.getMasterDokumen().getId())
                        .concat(File.separator)
                        .concat(regDokLama.getNamaFile());

                String subPathLocationTujuan = File.separator
                        .concat(registerDokumenBaru.getMasterDokumen().getId())
                        .concat(File.separator)
                        .concat(registerDokumenBaru.getNamaFile());

                localStorageService.move(subPathLocationAsal, subPathLocationTujuan);
            } catch (IOException ex) {
                throw new IllegalArgumentException("file dokumen tidak bisa disimpan");
            }
        }
        else {           
            try {
                localStorageService.delete(
                        regDokLama.getNamaFile(),
                        File.separator.concat(regDokLama.getMasterDokumen().getId())
                );
            } catch (IOException ex) {
                throw new IllegalArgumentException("file dokumen lama tidak bisa dihapus");
            }
        }
        
        return new RegisterDokumenDTO(registerDokumenBaru);
        
    } 
    
    @Path("/{idRegisterDokumen}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR, Role.UMUM})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(
            @Context ContainerRequestContext crc,
            @PathParam("idRegisterDokumen") String idRegisterDokumen) throws SQLException, UnspecifiedException {
        
        Otorisasi otorisasi = (Otorisasi) crc.getProperty("otoritas");
        Role role = (Role) crc.getProperty("role");
        Filter filter;
        List<Filter> fields_filter = new ArrayList<>();
        QueryParamFilters qFilter;
        filter = new Filter("id", idRegisterDokumen);
        fields_filter.add(filter);
        qFilter = new QueryParamFilters(false, null, fields_filter, null);
        RegisterDokumen regDokLama =  registerDokumenService
                                        .getDaftarData(qFilter)
                                        .getFirst();
        
        if(regDokLama ==  null) {
            throw new IllegalArgumentException("Register dokumen tidak terdaftar dalam sistem");
        }
        
        boolean isSuksesHapus = false;
        switch (role) {
            case ADMINISTRATOR -> {
                isSuksesHapus = registerDokumenService.delete(idRegisterDokumen);
            }
            case UMUM -> {
                fields_filter.clear();
                filter = new Filter("id_person", otorisasi.getPerson().getId());
                fields_filter.add(filter);
                filter = new Filter("id_perusahaan", regDokLama.getPerusahaan().getId());
                fields_filter.add(filter);
                qFilter = new QueryParamFilters(false, null, fields_filter, null);
                List<Pegawai> daftarPegawai = pegawaiService.getDaftarData(qFilter);
                
                if(daftarPegawai == null) {
                    throw new IllegalArgumentException("Akses ditolak");
                }
                
                isSuksesHapus = registerDokumenService.delete(idRegisterDokumen);
            }
            default -> {
                throw new UnspecifiedException(500, "Akses ditolak");
            }
        }
        
        if(isSuksesHapus == true) {
            try {
                localStorageService.delete(
                        regDokLama.getNamaFile(),
                        File.separator.concat(regDokLama.getMasterDokumen().getId())
                );
            } catch (IOException ex) {
               throw new IllegalArgumentException("Akses ditolak");
            }
        }
        
        JsonObject model = Json.createObjectBuilder()
                .add("status", isSuksesHapus == true ? "sukses" : "gagal")
                .build();            

        return model;
        
    }
    
    @Path("/jumlah")
    @GET
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR, Role.UMUM})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject getJumlahData(@QueryParam("filters") String qfilters) {
        try {
            if(qfilters != null) {
                Jsonb jsonb = JsonbBuilder.create();
                List<FilterDTO> filters = jsonb.fromJson(qfilters, new ArrayList<FilterDTO>(){}.getClass().getGenericSuperclass());
                
                JsonObject model = Json.createObjectBuilder()
                    .add("jumlah", 
                        registerDokumenService.getJumlahData(
                            filters
                                .stream()
                                .map(t -> t.toFilter())
                                .collect(Collectors.toList())
                        )
                    )
                    .build();            
            
                return model;
            }
            else {
                JsonObject model = Json.createObjectBuilder()
                    .add("jumlah", registerDokumenService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
    @Path("/file/sementara")
    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON})
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR, Role.UMUM})
    public RegisterDokumenSementaraDTO saveFileSementara(
            @Context ContainerRequestContext crc,
            @FormDataParam("registerDokumenSementara") String registerDokumenSementara,
            @FormDataParam("fileDokumen") File fileDokumen ) throws SQLException, UnspecifiedException {
        
        if(registerDokumenSementara == null || fileDokumen == null) {
            throw new UnspecifiedException(
                    400, "permintaaan ditolak karena data yang dikirim tidak sesuai");
        }
        
        Otorisasi otorisasi = (Otorisasi) crc.getProperty("otoritas");
        Role role = (Role) crc.getProperty("role");
        Jsonb jsonb = JsonbBuilder.create();
        
        RegisterDokumenSementaraDTO registerDokumenSementaraDTO;
        try {
            registerDokumenSementaraDTO = 
                jsonb.fromJson(registerDokumenSementara, 
                        RegisterDokumenSementaraDTO.class); 
        }
        catch(JsonbException | NullPointerException  e) {
            throw new UnspecifiedException(
                    400, "permintaaan ditolak karena format json tidak sesuai");
        }
        
        switch (role) {
            case ADMINISTRATOR -> {}
            case UMUM ->{
                List<Filter> fields_filter = new ArrayList<>();
                Filter filter = new Filter("id_person", otorisasi.getPerson().getId());
                fields_filter.add(filter);
                filter = new Filter("id_perusahaan", registerDokumenSementaraDTO.getId_perusahaan());
                fields_filter.add(filter);
                QueryParamFilters qFilter = new QueryParamFilters(false, null, fields_filter, null);
                List<Pegawai> daftarPegawai = pegawaiService.getDaftarData(qFilter);

                if(daftarPegawai == null) {
                    throw new IllegalArgumentException("Akses ditolak");
                }
            }
            default -> {
                throw new UnspecifiedException(400, "permintaaan ditolak karena role tidak sesuai");
            }
        }
        
        InputStream uploadedInputStream;
        String subPathLocation;
        JsonObject metaFile;        
        JsonObjectBuilder builder = Json.createObjectBuilder();
        RegisterDokumenSementara regDokSementara;
        
        if(registerDokumenSementaraDTO.getId() == null) {   
            try {
                uploadedInputStream = new FileInputStream(fileDokumen);
                subPathLocation = File.separator
                        .concat(registerDokumenSementaraDTO.getId_jenis_dokumen());
                metaFile = 
                    builder.add("BaseFileName", registerDokumenSementaraDTO.getNama_file())
                        .add("UserId", otorisasi.getId_user())
                        .add("UserFriendlyName", otorisasi.getPerson().getNama())
                        .add("UserCanWrite", true)
                        .build();
                regDokSementara =
                        new RegisterDokumenSementara(
                                registerDokumenSementaraDTO.getId(), 
                                registerDokumenSementaraDTO.getId_jenis_dokumen(), 
                                registerDokumenSementaraDTO.getId_perusahaan(), 
                                registerDokumenSementaraDTO.getNama_file(), 
                                null, 
                                metaFile
                        );
                regDokSementara = registerDokumenSementaraService.save(regDokSementara);
                localStorageService.upload(regDokSementara.getNamaFile(), 
                        uploadedInputStream, 
                        subPathLocation
                    ); 
                return new RegisterDokumenSementaraDTO(regDokSementara);
            } catch (FileNotFoundException ex) {
                throw new UnspecifiedException(
                    500, "permintaaan ditolak karena data file gagal dibuat");
            } catch (IOException ex) {
                throw new UnspecifiedException(
                    500, "permintaaan ditolak karena data file gagal disimpan");
            }
        }
        else {
            Filter filter;
            List<Filter> fields_filter = new ArrayList<>();
            QueryParamFilters qFilter;
            List<RegisterDokumenSementara> daftarRegDokTmp;
            filter = new Filter("id", registerDokumenSementaraDTO.getId());
            fields_filter.add(filter);
            qFilter = new QueryParamFilters(false, null, fields_filter, null);
            daftarRegDokTmp = registerDokumenSementaraService.getDaftarData(qFilter);

            if(daftarRegDokTmp == null) {
                throw new UnspecifiedException(
                    400, "permintaaan ditolak karena data yang dikirim tidak sesuai");
            }
            else {
                regDokSementara =  daftarRegDokTmp.getFirst();
                
                if(regDokSementara
                        .getIdPerusahaan()
                        .equalsIgnoreCase(
                            registerDokumenSementaraDTO.getId_perusahaan()
                        ) 
                    && regDokSementara
                        .getIdJenisDokumen()
                        .equalsIgnoreCase(
                            registerDokumenSementaraDTO.getId_jenis_dokumen()
                        )                            
                ) {
                    try {
                        regDokSementara = registerDokumenSementaraService.save(regDokSementara);
                        uploadedInputStream = new FileInputStream(fileDokumen);
                        subPathLocation = File.separator
                        .concat(registerDokumenSementaraDTO.getId_jenis_dokumen());
                        localStorageService.upload(regDokSementara.getNamaFile(),
                                uploadedInputStream,
                                subPathLocation 
                        );
                        return new RegisterDokumenSementaraDTO(regDokSementara);
                    } catch (IOException ex) {
                        throw new UnspecifiedException(
                            500, "permintaaan ditolak karena data file gagal disimpan");
                    }                    
                }
                else {
                    String idRegDokSementaraLama = regDokSementara.getId();
                    if(registerDokumenSementaraService
                            .delete(idRegDokSementaraLama)) {                        
                        try {
                            uploadedInputStream = new FileInputStream(fileDokumen);
                            subPathLocation = File.separator
                                    .concat(registerDokumenSementaraDTO.getId_jenis_dokumen());
                            metaFile =
                                    builder.add("BaseFileName", registerDokumenSementaraDTO.getNama_file())
                                            .add("UserId", otorisasi.getId_user())
                                            .add("UserFriendlyName", otorisasi.getPerson().getNama())
                                            .add("UserCanWrite", true)
                                            .build();
                            regDokSementara =
                                    new RegisterDokumenSementara(
                                            registerDokumenSementaraDTO.getId(),
                                            registerDokumenSementaraDTO.getId_jenis_dokumen(),
                                            registerDokumenSementaraDTO.getId_perusahaan(),
                                            registerDokumenSementaraDTO.getNama_file(),
                                            null,
                                            metaFile
                                    );
                            regDokSementara = registerDokumenSementaraService.save(regDokSementara);
                            localStorageService.upload(regDokSementara.getNamaFile(),
                                    uploadedInputStream,
                                    subPathLocation
                            );
                            return new RegisterDokumenSementaraDTO(regDokSementara);
                        } catch (FileNotFoundException ex) {
                            throw new UnspecifiedException(
                                500, "permintaaan ditolak karena data file tidak bisa dibuat");
                        } catch (IOException ex) {
                            throw new UnspecifiedException(
                                500, "permintaaan ditolak karena data file tidak bisa disimpan");
                        }
                    }
                    else {
                        throw new UnspecifiedException(
                            500, "permintaaan ditolak karena data lama gagal dihapus");
                    }
                }
            }            
        }  
    }
    
}
