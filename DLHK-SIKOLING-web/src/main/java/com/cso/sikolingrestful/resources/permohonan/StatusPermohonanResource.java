package com.cso.sikolingrestful.resources.permohonan;

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
import com.cso.sikoling.abstraction.entity.permohonan.StatusPermohonan;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("status_permohonan")
public class StatusPermohonanResource {
    
    @Inject
    private Service<StatusPermohonan> statusPermohonanService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<StatusPermohonanDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        List<StatusPermohonan> daftarStatusPermohonan;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = 
                        jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);
                daftarStatusPermohonan = statusPermohonanService
                        .getDaftarData(queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarStatusPermohonan == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarStatusPermohonan
                        .stream()
                        .map(t -> new StatusPermohonanDTO(t))
                        .collect(Collectors.toList());
                }
            }
            else {
                daftarStatusPermohonan = statusPermohonanService.getDaftarData(null);
                
                if(daftarStatusPermohonan == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarStatusPermohonan
                        .stream()
                        .map(t -> new StatusPermohonanDTO(t))
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
    public StatusPermohonanDTO save(StatusPermohonanDTO statusPermohonanDTO) throws SQLException { 
        
        try {  
            statusPermohonanDTO.setId(null);
            return new StatusPermohonanDTO(statusPermohonanService.save(statusPermohonanDTO.toStatusPermohonan()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json status permohonan harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public StatusPermohonanDTO update(@PathParam("idLama") String idLama, StatusPermohonanDTO statusPermohonanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(statusPermohonanDTO.getId());
            if(isIdSame) {
                return new StatusPermohonanDTO(statusPermohonanService.update(statusPermohonanDTO.toStatusPermohonan()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru status permohonan harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json status permohonan harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public StatusPermohonanDTO updateId(@PathParam("idLama") String idLama, StatusPermohonanDTO statusPermohonanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(statusPermohonanDTO.getId());

            if(!isIdSame) {
                return new StatusPermohonanDTO(statusPermohonanService.updateId(idLama, statusPermohonanDTO.toStatusPermohonan()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru status permohonan harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json status permohonan harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idStatusPermohonan}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idStatusPermohonan") String idStatusPermohonan) throws SQLException {
        
        boolean isDigit = idStatusPermohonan.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", statusPermohonanService.delete(idStatusPermohonan) == true ? "sukses" : "gagal")
                    .build();            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id status permohonan harus bilangan");
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
                        statusPermohonanService.getJumlahData(
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
                    .add("jumlah", statusPermohonanService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
