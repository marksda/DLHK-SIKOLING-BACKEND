package com.cso.sikolingrestful.resources.security;

import com.cso.sikoling.abstraction.entity.security.HakAkses;
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
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("hak_akses")
public class HakAksesResource {
    
    @Inject
    private Service<HakAkses> hakAksesService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<HakAksesDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return hakAksesService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new HakAksesDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return hakAksesService.getDaftarData(null)
                        .stream()
                        .map(t -> new HakAksesDTO(t))
                        .collect(Collectors.toList());
            }             
        } 
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
        
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public HakAksesDTO save(HakAksesDTO hakAksesDTO) throws SQLException { 
        
        try {            
            return new HakAksesDTO(hakAksesService.save(hakAksesDTO.toHakAkses()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json hak akses harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public HakAksesDTO update(@PathParam("idLama") String idLama, HakAksesDTO hakAksesDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(hakAksesDTO.getId());
            if(isIdSame) {
                return new HakAksesDTO(hakAksesService.update(hakAksesDTO.toHakAkses()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru hak akses harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json hak akses harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public HakAksesDTO updateId(@PathParam("idLama") String idLama, HakAksesDTO hakAksesDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(hakAksesDTO.getId());

            if(!isIdSame) {
                return new HakAksesDTO(hakAksesService.updateId(idLama, hakAksesDTO.toHakAkses()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru hak akses harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json hak akses harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idHakAkses}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idHakAkses") String idHakAkses) throws SQLException {
        
        boolean isDigit = idHakAkses.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", hakAksesService.delete(idHakAkses) == true ? "sukses" : "gagal")
                    .build();            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id hak akses harus bilanagan panjang 2 digit");
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
                    .add(
                        "jumlah", 
                        hakAksesService.getJumlahData(
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
                    .add("jumlah", hakAksesService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
