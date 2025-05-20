package com.cso.sikolingrestful.resources.security.oauth;

import com.cso.sikolingrestful.resources.security.oauth.TokenDTO;
import com.cso.sikolingrestful.resources.security.oauth.KeyDTO;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.service.DAOService;
import com.cso.sikoling.abstraction.service.DAOTokenService;
import com.cso.sikolingrestful.exception.KeyException;
import com.cso.sikolingrestful.resources.security.CredentialDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;

@Stateless
@LocalBean
@Path("token")
public class TokenResource {
    
    @Inject
    private DAOTokenService<Token> tokenService;
    
    @Inject
    private DAOService<Key> keyService;
    
//    @Inject
//    private SecurityContext SecurityContext;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/generate_key/{idRealm}/{idJwa}/{idEncodingScheme}")
    public KeyDTO generateKey(
            @PathParam("idRealm") String idRealm, 
            @PathParam("idJwa") String idJwa, 
            @PathParam("idEncodingScheme") String idEncodingScheme) {  
        Key key = tokenService.generateKey(idRealm, idJwa, idEncodingScheme);
        try {
            keyService.save(key);
            return new KeyDTO(key);
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Gagal mengenerate key");
        }        
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{idRealm}/{idKey}/{idEncodingScheme}")
    public TokenDTO getToken(
            @PathParam("idRealm") String idRealm, 
            @PathParam("idKey") String idKey, 
            @PathParam("idEncodingScheme") String idEncodingScheme, 
            CredentialDTO credentialDTO) throws KeyException {
        Token token = tokenService.getToken(credentialDTO.toCredential(), idRealm, idKey, idEncodingScheme);
        if(token != null) {
            return new TokenDTO(token);
        }
        else {
            throw new KeyException(500, "Realm not found");
        }
    }
    
}
