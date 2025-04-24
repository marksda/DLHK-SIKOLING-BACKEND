package com.cso.sikolingrestful.resources.security;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikoling.abstraction.entity.security.Token;
import com.cso.sikoling.abstraction.service.DAOTokenService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
@Path("token")
public class TokenResource {
    
    @Inject
    private DAOTokenService<Token> tokenService;
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public TokenDTO getToken(CredentialDTO credentialDTO) throws IOException {
        try {
            return new TokenDTO(tokenService.getToken(credentialDTO.toCredential()));
        } catch (SQLException ex) {
            Logger.getLogger(TokenResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
