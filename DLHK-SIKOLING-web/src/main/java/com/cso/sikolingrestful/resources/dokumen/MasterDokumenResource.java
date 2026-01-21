package com.cso.sikolingrestful.resources.dokumen;

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
import com.cso.sikoling.abstraction.entity.dokumen.MasterDokumen;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.exception.UnspecifiedException;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("dokumen")
public class MasterDokumenResource {
    
    @Inject
    private Service<MasterDokumen> masterDokumenService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<MasterDokumenDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) throws UnspecifiedException {
        
        List<MasterDokumen> daftarMasterDokumen;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(
                        queryParamsStr, QueryParamFiltersDTO.class);
                daftarMasterDokumen = masterDokumenService.getDaftarData(
                        queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarMasterDokumen == null) {
                    throw new UnspecifiedException(500, "daftar kbli tidak ada");
                }
                else {
                    return daftarMasterDokumen
                        .stream()
                        .map(t -> new MasterDokumenDTO(t))
                        .collect(Collectors.toList());
                }                        
            }
            else {
                daftarMasterDokumen = masterDokumenService.getDaftarData(null);
                
                if(daftarMasterDokumen == null) {
                    throw new UnspecifiedException(500, "daftar kbli tidak ada");
                }
                else {
                    return daftarMasterDokumen
                        .stream()
                        .map(t -> new MasterDokumenDTO(t))
                        .collect(Collectors.toList());
                } 
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
    public MasterDokumenDTO save(MasterDokumenDTO dokumenDTO) throws SQLException { 
        
        try {            
            return new MasterDokumenDTO(masterDokumenService.save(dokumenDTO.toDokumen()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json dokumen harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public MasterDokumenDTO update(@PathParam("idLama") String idLama, MasterDokumenDTO dokumenDTO) throws SQLException {
        
        try {                
            if(idLama.equals(dokumenDTO.getId())) {
                return new MasterDokumenDTO(masterDokumenService.update(dokumenDTO.toDokumen()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru dokumen harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json dokumen harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public MasterDokumenDTO updateId(@PathParam("idLama") String idLama, MasterDokumenDTO dokumenDTO) throws SQLException {
        
        try {                
            if(idLama.equals(dokumenDTO.getId())) {
                throw new IllegalArgumentException("id lama dan baru dokumen harus beda");
            }
            else {
                return new MasterDokumenDTO(masterDokumenService.updateId(idLama, dokumenDTO.toDokumen()));
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json dokumen harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idDokumen}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idDokumen") String idDokumen) throws SQLException {
            
        JsonObject model = Json.createObjectBuilder()
                .add("status", masterDokumenService.delete(idDokumen) == true ? "sukses" : "gagal")
                .build();


        return model;
        
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
                        masterDokumenService.getJumlahData(
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
                    .add("jumlah", masterDokumenService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
