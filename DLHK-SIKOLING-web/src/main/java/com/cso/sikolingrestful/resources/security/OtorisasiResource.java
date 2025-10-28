package com.cso.sikolingrestful.resources.security;

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
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikoling.abstraction.service.Service;

@Stateless
@LocalBean
@Path("otorisasi")
public class OtorisasiResource {
    
    @Inject
    private Service<Otorisasi> otorisasiService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    public List<OtorisasiDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return otorisasiService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new OtorisasiDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return otorisasiService.getDaftarData(null)
                        .stream()
                        .map(t -> new OtorisasiDTO(t))
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
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    public OtorisasiDTO save(OtorisasiDTO otorisasiDTO) throws SQLException { 
        
        try {            
            return new OtorisasiDTO(otorisasiService.save(otorisasiDTO.toOtorisasi()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json otorisasi harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    public OtorisasiDTO update(@PathParam("idLama") String idLama, OtorisasiDTO otorisasiDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(otorisasiDTO.getId());
            if(isIdSame) {
                return new OtorisasiDTO(otorisasiService.update(otorisasiDTO.toOtorisasi()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru otorisasi harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json otorisasi harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    public OtorisasiDTO updateId(@PathParam("idLama") String idLama, OtorisasiDTO otorisasiDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(otorisasiDTO.getId());

            if(!isIdSame) {
                return new OtorisasiDTO(otorisasiService.updateId(idLama, otorisasiDTO.toOtorisasi()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru otorisasi harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json otorisasi harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idOtorisasi}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    public JsonObject delete(@PathParam("idOtorisasi") String idOtorisasi) throws SQLException {
        
        boolean isDigit = idOtorisasi.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", otorisasiService.delete(idOtorisasi) == true ? "sukses" : "gagal")
                    .build();            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id otorisasi harus bilanagan");
        }        
        
    }
    
}
