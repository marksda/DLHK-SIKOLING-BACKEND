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
import com.cso.sikolingrestful.resources.QueryParamFiltersDTO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.QueryParam;
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
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KeyDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
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
            throw new JsonbException("format query data json tidak sesuai");
        }
        
    }
    
}
