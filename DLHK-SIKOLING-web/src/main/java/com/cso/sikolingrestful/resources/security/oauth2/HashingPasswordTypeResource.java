package com.cso.sikolingrestful.resources.security.oauth2;

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
import com.cso.sikoling.abstraction.entity.security.oauth2.HashingPasswordType;
import com.cso.sikoling.abstraction.service.Service;

@Stateless
@LocalBean
@Path("hashing_password_type")
public class HashingPasswordTypeResource {
    
    @Inject
    private Service<HashingPasswordType> hashingPasswordTypeService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<HashingPasswordTypeDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return hashingPasswordTypeService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new HashingPasswordTypeDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return hashingPasswordTypeService.getDaftarData(null)
                        .stream()
                        .map(t -> new HashingPasswordTypeDTO(t))
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
    public HashingPasswordTypeDTO save(HashingPasswordTypeDTO hakAksesDTO) throws SQLException { 
        
        try {            
            return new HashingPasswordTypeDTO(hashingPasswordTypeService.save(hakAksesDTO.toHashingPasswordType()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json hashing password type harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public HashingPasswordTypeDTO update(@PathParam("idLama") String idLama, HashingPasswordTypeDTO hakAksesDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(hakAksesDTO.getId());
            if(isIdSame) {
                return new HashingPasswordTypeDTO(hashingPasswordTypeService.update(hakAksesDTO.toHashingPasswordType()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru hashing password type harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json hashing password type harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public HashingPasswordTypeDTO updateId(@PathParam("idLama") String idLama, HashingPasswordTypeDTO hakAksesDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(hakAksesDTO.getId());

            if(!isIdSame) {
                return new HashingPasswordTypeDTO(hashingPasswordTypeService.updateId(idLama, hakAksesDTO.toHashingPasswordType()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru hashing password type harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json hashing password type harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idHashingPasswordType}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idHashingPasswordType") String idHashingPasswordType) throws SQLException {
        
        boolean isDigit = idHashingPasswordType.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", hashingPasswordTypeService.delete(idHashingPasswordType) == true ? "sukses" : "gagal")
                    .build();            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id hashing password type harus bilangan panjang 2 digit");
        }        
        
    }
    
}
