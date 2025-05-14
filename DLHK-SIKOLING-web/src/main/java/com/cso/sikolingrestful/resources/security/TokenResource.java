package com.cso.sikolingrestful.resources.security;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.service.DAOTokenService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.SQLException;

@Stateless
@LocalBean
@Path("token")
public class TokenResource {
    
    @Inject
    private DAOTokenService<Token> tokenService;
    
//    @Inject
//    private SecurityContext SecurityContext;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/generate_key/{signatureAlgoritma}")
    public SecretKeyDTO getKey(@PathParam("signatureAlgoritma") String signatureAlgoritma) {        
        return new SecretKeyDTO(tokenService.generateSecretKey(signatureAlgoritma));
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public TokenDTO getToken(CredentialDTO credentialDTO) throws IOException {
        try {
            Token token = tokenService.getToken(credentialDTO.toCredential());
            if(token != null) {
                return new TokenDTO(token);
            }
            else {
                throw new IllegalArgumentException("credential ditolak");
            }
        } catch (SQLException ex) {
//            Logger.getLogger(TokenResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("credential ditolak");
        }
    }
    
}
