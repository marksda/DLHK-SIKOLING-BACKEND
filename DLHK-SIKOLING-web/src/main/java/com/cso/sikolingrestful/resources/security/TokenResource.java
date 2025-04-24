package com.cso.sikolingrestful.resources.security;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikoling.abstraction.entity.security.Autorisasi;
import com.cso.sikoling.abstraction.service.DAOService;
import java.io.IOException;

@Stateless
@LocalBean
@Path("autorisasi")
public class TokenResource {
    
//    @Inject
//    private DAOService<Autorisasi> autorisasiService;
//    
//    @Path("get_token")
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    public TokenDTO getToken(CredentialDTO credentialDTO) throws IOException {
//        return new ResponTokenDTO(userService.getToken(u.toCredential()));
//    }
    
    
}
