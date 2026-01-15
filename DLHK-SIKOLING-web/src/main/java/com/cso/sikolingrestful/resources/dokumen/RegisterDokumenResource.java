package com.cso.sikolingrestful.resources.dokumen;

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
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
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
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RegisterDokumenDTO save(RegisterDokumenDTO registerDokumenDTO) throws SQLException { 
        
        try {            
            return new RegisterDokumenDTO(registerDokumenService.save(registerDokumenDTO.toRegisterDokumen()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json register dokumen harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RegisterDokumenDTO update(@PathParam("idLama") String idLama, RegisterDokumenDTO registerDokumenDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(registerDokumenDTO.getId());
            if(isIdSame) {
                return new RegisterDokumenDTO(registerDokumenService.update(registerDokumenDTO.toRegisterDokumen()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru register dokumen harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json register dokumen harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RegisterDokumenDTO updateId(@PathParam("idLama") String idLama, RegisterDokumenDTO registerDokumenDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(registerDokumenDTO.getId());

            if(!isIdSame) {
                return new RegisterDokumenDTO(registerDokumenService.updateId(idLama, registerDokumenDTO.toRegisterDokumen()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru register harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json register dokumen harus disertakan di body put request");
        }
        
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
