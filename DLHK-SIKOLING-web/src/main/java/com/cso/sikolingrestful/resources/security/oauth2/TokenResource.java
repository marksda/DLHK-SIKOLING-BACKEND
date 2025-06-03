package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.Autorisasi;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.entity.security.oauth2.User;
import com.cso.sikoling.abstraction.service.KeyService;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.resources.security.CredentialDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import com.cso.sikoling.abstraction.service.TokenService;
import com.cso.sikoling.abstraction.service.UserService;
import com.cso.sikolingrestful.exception.UnspecifiedException;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
@Path("token")
public class TokenResource {
    
    @Inject
    private TokenService<Token> tokenService;
    
    @Inject
    private UserService<User> userService;
    
    @Inject
    private Service<Autorisasi> autorisasiService;
    
    @Inject
    private KeyService<Key> keyService;    
        
//    @Inject
//    private SecurityContext SecurityContext;
  
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{idRealm}/{idJwa}/{idKey}")
    public TokenDTO generateToken(
            @PathParam("idRealm") String idRealm, 
            @PathParam("idJwa") String idJwa, 
            @PathParam("idKey") String idKey,
            CredentialDTO credentialDTO) throws UnspecifiedException {
        
        Token token;
        User user = userService.authentication(credentialDTO.toCredential());
        
        if(user != null) {
            List<Filter> filterAutorisasi = new ArrayList<>();
            Filter filter = new Filter("id_user", user.getId());
            filterAutorisasi.add(filter);
            QueryParamFilters qFilterAutorisasi = new QueryParamFilters(false, null, filterAutorisasi, null);
            Autorisasi autorisasi = autorisasiService.getDaftarData(qFilterAutorisasi).getFirst();
            
            List<Filter> filterKey = new ArrayList<>();
            filter = new Filter("realm", idRealm);
            filterKey.add(filter);
            filter = new Filter("jwa", idJwa);
            filterKey.add(filter);
            filter = new Filter("id", idKey);
            filterKey.add(filter);
            QueryParamFilters qFilterKey = new QueryParamFilters(false, null, filterKey, null);
            
            Key key = keyService.getDaftarData(qFilterKey).getFirst();
            
            token = tokenService.generateToken(key, autorisasi);
            
            if(token != null) {
                return new TokenDTO(token);
            }
            else {
                throw new UnspecifiedException(500, "Token gagal dibuat"); 
            }            
        }
        else {
           throw new UnspecifiedException(500, "Credential ditolak"); 
        }
        
    }
    
}
