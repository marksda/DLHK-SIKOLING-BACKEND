package com.cso.sikolingrestful.resources.security.oauth;

import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikoling.abstraction.service.DAOKeyService;
import com.cso.sikolingrestful.exception.KeyException;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;

@Stateless
@LocalBean
@Path("key")
public class KeyResource {
    
    @Inject
    private DAOKeyService<Key> keyService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/generate/{idRealm}/{idJwa}/{idEncodingScheme}")
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
    
}
