package com.cso.sikolingrestful.resources.dokumen;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumen;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumenSementara;
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
import com.cso.sikolingrestful.resources.FilterDTO;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.NotAuthorizedException;
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
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<RegisterDokumenDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return registerDokumenService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new RegisterDokumenDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return registerDokumenService.getDaftarData(null)
                        .stream()
                        .map(t -> new RegisterDokumenDTO(t))
                        .collect(Collectors.toList());
            }             
        } 
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
        
    }
    
    @POST
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR, Role.UMUM})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RegisterDokumenDTO save(@Context ContainerRequestContext crc,
                RegisterDokumenDTO registerDokumenDTO) throws SQLException {  
        Filter filter;
        List<Filter> fields_filter = new ArrayList<>();
        QueryParamFilters qFilter;
        RegisterDokumenSementara regDokSementara;
        Otorisasi otorisasi = (Otorisasi) crc.getProperty("otoritas");
        Role role = (Role) crc.getProperty("role");
        JsonObject metaFile;
        JsonObjectBuilder builder;
        
        switch (role) {
            case ADMINISTRATOR -> {
                filter = new Filter("id", registerDokumenDTO.getId());
                fields_filter.add(filter);
                qFilter = new QueryParamFilters(
                                        false, null, 
                                        fields_filter, null
                                    );
                regDokSementara = registerDokumenSementaraService
                                        .getDaftarData(qFilter)
                                        .getFirst();
                if(regDokSementara != null) {
                    builder = Json.createObjectBuilder(regDokSementara.getMetaFile());
                    if(registerDokumenDTO.getIs_validated()) {
                        builder.add("UserCanWrite", false);
                        metaFile = builder.build();
                    }
                    else {
                        metaFile = builder.build();
                    }
                }
                else {
                    throw new NotAuthorizedException("Tidak sesuai dengan data sementara");
                }
            }
            default -> {
                filter = new Filter("id", registerDokumenDTO.getId());
                fields_filter.add(filter);
                filter = new Filter(
                                "id_perusahaan", 
                                registerDokumenDTO.getPerusahaan().getId()
                            );
                fields_filter.add(filter);
                qFilter = new QueryParamFilters(
                                        false, null, 
                                        fields_filter, null
                                    );
                regDokSementara = registerDokumenSementaraService
                                        .getDaftarData(qFilter)
                                        .getFirst();   
                
                if(regDokSementara != null) {
                    metaFile = regDokSementara.getMetaFile();
                }
                else {
                    throw new NotAuthorizedException("Tidak sesuai dengan data sementara");
                }
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
            RegisterDokumenDTO regDokDTOBaru) throws SQLException {
        
        Otorisasi otorisasi = (Otorisasi) crc.getProperty("otoritas");
        Role role = (Role) crc.getProperty("role");
        Filter filter;
        List<Filter> fields_filter = new ArrayList<>();
        QueryParamFilters qFilter;
        filter = new Filter("id", idLama);
                fields_filter.add(filter);
                qFilter = new QueryParamFilters(
                                        false, null, 
                                        fields_filter, null
                                    );
        RegisterDokumen regDokLama =  registerDokumenService
                                        .getDaftarData(qFilter)
                                        .getFirst();
        
        if(regDokLama ==  null) {
            throw new IllegalArgumentException("Register dokumen tidak terdaftar dalam sistem");
        }
        
        if(!(regDokLama.getDokumen().getId().equals(regDokDTOBaru.getDokumen().getId())
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
                    regDokLama.getDokumen(), 
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
                metaFile = regDokLama.getMetaFile();
                registerDokumenBaru =  new RegisterDokumen(
                    regDokLama.getId(), 
                    regDokLama.getPerusahaan(), 
                    regDokLama.getDokumen(), 
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
        }
        
        registerDokumenBaru = registerDokumenService.update(registerDokumenBaru);
        
        fields_filter.clear();
        filter.setField_name("id");
        filter.setValue(registerDokumenBaru.getId());
        fields_filter.add(filter);
        qFilter.setFields_filter(fields_filter);
        
        RegisterDokumenSementara dokSementara = 
                registerDokumenSementaraService.getDaftarData(qFilter).getFirst();
        
        if(dokSementara != null) {
            try {
                String subPathLocationTujuan = File.separator
                        .concat(regDokLama.getDokumen().getId())
                        .concat(File.separator)
                        .concat(regDokLama.getNamaFile());
                
                String subPathLocationAsal = File.separator
                        .concat(registerDokumenBaru.getDokumen().getId())
                        .concat(File.separator)
                        .concat(registerDokumenBaru.getNamaFile());
                
                localStorageService.move(subPathLocationAsal, subPathLocationTujuan);
            } catch (IOException ex) {
                System.getLogger(RegisterDokumenResource.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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
            RegisterDokumenDTO regDokDTOBaru) throws SQLException {
        
        Otorisasi otorisasi = (Otorisasi) crc.getProperty("otoritas");
        Role role = (Role) crc.getProperty("role");
        Filter filter;
        List<Filter> fields_filter = new ArrayList<>();
        QueryParamFilters qFilter;
        filter = new Filter("id", idLama);
                fields_filter.add(filter);
                qFilter = new QueryParamFilters(
                                        false, null, 
                                        fields_filter, null
                                    );
        RegisterDokumen regDokLama =  registerDokumenService
                                        .getDaftarData(qFilter)
                                        .getFirst();
        
        if(regDokLama ==  null) {
            throw new IllegalArgumentException("Register dokumen tidak terdaftar dalam sistem");
        }
        
        if(!(regDokLama.getDokumen().getId().equals(regDokDTOBaru.getDokumen().getId())
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
            default -> {
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
        }
        
        registerDokumenBaru = registerDokumenService.update(registerDokumenBaru);
        
        fields_filter.clear();
        filter.setField_name("id");
        filter.setValue(registerDokumenBaru.getId());
        fields_filter.add(filter);
        qFilter.setFields_filter(fields_filter);
        
        RegisterDokumenSementara dokSementara = 
                registerDokumenSementaraService.getDaftarData(qFilter).getFirst();
        
        if(dokSementara ==  null) {
            try {
                String subPathLocationAsal = File.separator
                        .concat(regDokLama.getDokumen().getId())
                        .concat(File.separator)
                        .concat(regDokLama.getNamaFile());

                String subPathLocationTujuan = File.separator
                        .concat(registerDokumenBaru.getDokumen().getId())
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
                        File.separator.concat(regDokLama.getDokumen().getId())
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
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idRegisterDokumen") String idRegisterDokumen) throws SQLException {
        
        boolean isDigit = idRegisterDokumen.matches("[0-9]{11}");
        
        if(isDigit) {	
            JsonObject model = Json.createObjectBuilder()
                    .add("status", registerDokumenService.delete(idRegisterDokumen) == true ? "sukses" : "gagal")
                    .build();            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id register dokumen harus bilangan 11 digit");
        }        
        
    }
    
    @Path("/jumlah")
    @GET
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
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
            @FormDataParam("fileDokumen") File fileDokumen ) throws SQLException {
        try {
            Otorisasi otorisasi = (Otorisasi) crc.getProperty("otoritas");
            Jsonb jsonb = JsonbBuilder.create();
            RegisterDokumenSementaraDTO registerDokumenSementaraDTO = jsonb.fromJson(
                                    registerDokumenSementara, RegisterDokumenSementaraDTO.class);   
                        
            InputStream uploadedInputStream = new FileInputStream(fileDokumen);
            String subPathLocation = File.separator
                    .concat(registerDokumenSementaraDTO.getId_jenis_dokumen());
            
            try {
                JsonObject metaFile = Json.createObjectBuilder()
                        .add("BaseFileName", registerDokumenSementaraDTO.getNama_file())
                        .add("UserId", otorisasi.getId_user())
                        .add("UserFriendlyName", otorisasi.getPerson().getNama())
                        .add("UserCanWrite", true)
                        .build();
                RegisterDokumenSementara dokSementara =
                        new RegisterDokumenSementara(
                                registerDokumenSementaraDTO.getId(), 
                                registerDokumenSementaraDTO.getId_jenis_dokumen(), 
                                registerDokumenSementaraDTO.getId_perusahaan(), 
                                registerDokumenSementaraDTO.getNama_file(), 
                                null, 
                                metaFile
                        );
                        
                dokSementara = registerDokumenSementaraService.save(dokSementara);
                localStorageService.upload(
                        dokSementara.getNamaFile(), 
                        uploadedInputStream, 
                        subPathLocation
                    ); 
                
                return new RegisterDokumenSementaraDTO(dokSementara);
            } 
            catch (NullPointerException e) {
                throw new IllegalArgumentException("data json dokumen sementara harus disertakan di body post request");
            } catch (IOException ex) {
                throw new IllegalArgumentException("file dokumen sementara tidak bisa disimpan");
            } 
        } catch (JsonbException | FileNotFoundException e) {
            throw new JsonbException("file error");
        }
    }
    
}
