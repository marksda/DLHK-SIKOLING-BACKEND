package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikolingrestful.exception.KeyException;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;
import com.cso.sikoling.abstraction.service.KeyService;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.exception.UnspecifiedException;
import com.cso.sikolingrestful.resources.FilterDTO;
import com.cso.sikolingrestful.resources.QueryParamFiltersDTO;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@LocalBean
@Path("key")
public class KeyResource {
    
    @Inject
    private KeyService<Key> keyService;    
    
    @GET
    @Path("/generate/{idRealm}/{idJwa}/{idEncodingScheme}")
    @Produces({MediaType.APPLICATION_JSON})
    public KeyDTO generateKey(
            @PathParam("idRealm") String idRealm,
            @PathParam("idJwa") String idJwa, 
            @PathParam("idEncodingScheme") String idEncodingScheme) throws KeyException {  
        Key key = keyService.generateKey(idRealm, idJwa, idEncodingScheme);
        try {
            keyService.save(key);
            return new KeyDTO(key);
        } catch (SQLException ex) {
            throw new KeyException(500, "Gagal membuat key");
        }        
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KeyDTO save(KeyDTO keyDTO) throws SQLException {         
        try { 
            return new KeyDTO(keyService.save(keyDTO.toKey()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json key harus disertakan di body post request");
        }  
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KeyDTO update(@PathParam("idLama") String idLama, KeyDTO keyDTO) throws SQLException, UnspecifiedException {
        
        try {                
            boolean isIdSame = idLama.equals(keyDTO.getId());
            
            if(isIdSame) {
                return new KeyDTO(keyService.update(keyDTO.toKey()));
            }
            else {
                throw new UnspecifiedException(500, "id lama dan baru key harus sama");
            }
        } catch (NullPointerException e) {
            throw new UnspecifiedException(500, "data json key harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KeyDTO updateId(@PathParam("idLama") String idLama, KeyDTO keyDTO) throws UnspecifiedException, SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(keyDTO.getId());

            if(!isIdSame) {
                return new KeyDTO(keyService.updateId(idLama, keyDTO.toKey()));
            }
            else {
                throw new UnspecifiedException(500, "id lama dan baru key harus beda");
            }
        } catch (NullPointerException e) {
            throw new UnspecifiedException(500, "data json key harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idKey}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKey") String idKey) throws SQLException {
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", keyService.delete(idKey) == true ? "sukses" : "gagal")
                    .build();
            
            return model;
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KeyDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) throws UnspecifiedException {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return keyService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new KeyDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return keyService.getDaftarData(null)
                        .stream()
                        .map(t -> new KeyDTO(t))
                        .collect(Collectors.toList());
            }             
        } 
        catch (JsonbException e) {
            throw new UnspecifiedException(500, "format query data json tidak sesuai");
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
                        keyService.getJumlahData(
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
                    .add("jumlah", keyService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
