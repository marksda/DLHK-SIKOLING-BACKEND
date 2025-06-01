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
import com.cso.sikoling.abstraction.entity.security.oauth2.Realm;
import com.cso.sikoling.abstraction.service.Service;

@Stateless
@LocalBean
@Path("realm")
public class RealmResource {
    
    @Inject
    private Service<Realm> realmService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<RealmDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return realmService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new RealmDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return realmService.getDaftarData(null)
                        .stream()
                        .map(t -> new RealmDTO(t))
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
    public RealmDTO save(RealmDTO realmDTO) throws SQLException {         
        try {            
            return new RealmDTO(realmService.save(realmDTO.toRealm()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json realm harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RealmDTO update(@PathParam("idLama") String idLama, RealmDTO realmDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(realmDTO.getId());
            if(isIdSame) {
                return new RealmDTO(realmService.update(realmDTO.toRealm()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru realm harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json realm harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RealmDTO updateId(@PathParam("idLama") String idLama, RealmDTO realmDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(realmDTO.getId());

            if(!isIdSame) {
                return new RealmDTO(realmService.updateId(idLama, realmDTO.toRealm()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru realm harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json realm harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idRealm}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idRealm") String idRealm) throws SQLException {            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", realmService.delete(idRealm) == true ? "sukses" : "gagal")
                    .build();
            
            return model;        
    }
    
}
