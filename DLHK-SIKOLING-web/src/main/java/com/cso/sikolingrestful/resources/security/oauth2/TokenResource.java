package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.entity.security.oauth2.User;
import com.cso.sikoling.abstraction.service.KeyService;
import com.cso.sikolingrestful.resources.security.CredentialDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import com.cso.sikoling.abstraction.service.TokenService;
import com.cso.sikoling.abstraction.service.UserService;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.exception.UnspecifiedException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.DELETE;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@LocalBean
@Path("token")
public class TokenResource {
    
    @Inject
    private TokenService<Token> tokenService;
    
    @Inject
    private UserService<User> userService;
    
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
            CredentialDTO credentialDTO) throws UnspecifiedException, SQLException {
        
        Token token;
        User user = userService.authentication(credentialDTO.toCredential());
        
        if(user != null) {            
            List<Filter> filterKey = new ArrayList<>();
            Filter filter = new Filter("realm", idRealm);
            filterKey.add(filter);
            filter = new Filter("jwa", idJwa);
            filterKey.add(filter);
            filter = new Filter("id", idKey);
            filterKey.add(filter);
            QueryParamFilters qFilterKey = new QueryParamFilters(false, null, filterKey, null);
            
            Key key = keyService.getDaftarData(qFilterKey).getFirst();
            
            Header jwsHeader = Jwts.header()
                    .keyId(key.getId())
                    .add("typ", "JWT")
                    .build();
            
            
            Map jwsPayload = new HashMap<String, Object>();
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();
            cal.add(Calendar.YEAR, 1); 
            Date nextYear = cal.getTime();
        
            jwsPayload.put("iss", "dlhk sidoarjo");
            jwsPayload.put("sub", key.getId_realm());
            jwsPayload.put("aud", user.getId());
            jwsPayload.put("exp", nextYear);
            jwsPayload.put("iat", today);
            
            
            token = tokenService.generateToken(key, jwsHeader, jwsPayload);
            
            if(token != null) {
                tokenService.save(token);
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
    
    @Path("/{idToken}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idToken") String idToken) throws SQLException {            
        JsonObject model = Json.createObjectBuilder()
                .add("keterangan", tokenService.delete(idToken) == true ? "sukses" : "gagal")
                .build();

        return model;
    }
}
