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
import com.cso.sikoling.abstraction.entity.security.Autorisasi;
import com.cso.sikoling.abstraction.service.DAOService;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;

@Stateless
@LocalBean
@Path("autorisasi")
public class AutorisasiResource {
    
    @Inject
    private DAOService<Autorisasi> autorisasiService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    public List<AutorisasiDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return autorisasiService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new AutorisasiDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return autorisasiService.getDaftarData(null)
                        .stream()
                        .map(t -> new AutorisasiDTO(t))
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
    public AutorisasiDTO save(AutorisasiDTO autorisasiDTO) throws SQLException { 
        
        try {            
            return new AutorisasiDTO(autorisasiService.save(autorisasiDTO.toAutorisasi()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json autorisasi harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    public AutorisasiDTO update(@PathParam("idLama") String idLama, AutorisasiDTO autorisasiDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(autorisasiDTO.getId());
            if(isIdSame) {
                return new AutorisasiDTO(autorisasiService.update(autorisasiDTO.toAutorisasi()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru autorisasi harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json autorisasi harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    public AutorisasiDTO updateId(@PathParam("idLama") String idLama, AutorisasiDTO autorisasiDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(autorisasiDTO.getId());

            if(!isIdSame) {
                return new AutorisasiDTO(autorisasiService.updateId(idLama, autorisasiDTO.toAutorisasi()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru autorisasi harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json autorisasi harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idAutorisasi}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    public JsonObject delete(@PathParam("idAutorisasi") String idAutorisasi) throws SQLException {
        
        boolean isDigit = idAutorisasi.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", autorisasiService.delete(idAutorisasi) == true ? "sukses" : "gagal")
                    .build();            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id autorisasi harus bilanagan");
        }        
        
    }
    
}
