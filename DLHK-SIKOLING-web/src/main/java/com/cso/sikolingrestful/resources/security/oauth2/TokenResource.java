package com.cso.sikolingrestful.resources.security.oauth2;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikolingrestful.exception.KeyException;
import com.cso.sikolingrestful.resources.security.CredentialDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import com.cso.sikoling.abstraction.service.TokenService;

@Stateless
@LocalBean
@Path("token")
public class TokenResource {
    
    @Inject
    private TokenService<Token> tokenService;
        
//    @Inject
//    private SecurityContext SecurityContext;
  
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{idRealm}/{idKey}/{idEncodingScheme}")
    public TokenDTO getToken(
            @PathParam("idRealm") String idRealm, 
            @PathParam("idKey") String idKey, 
            @PathParam("idEncodingScheme") String idEncodingScheme, 
            CredentialDTO credentialDTO) throws KeyException {
//        Token token = tokenService.generateToken(credentialDTO.toCredential(), idRealm, idKey, idEncodingScheme);
        Token token = null;
        if(token != null) {
            return new TokenDTO(token);
        }
        else {
            throw new KeyException(500, "Realm not found");
        }
    }
    
}
