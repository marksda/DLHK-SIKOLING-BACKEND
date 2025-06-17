package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.Autorisasi;
import com.cso.sikoling.abstraction.entity.security.oauth2.Key;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikolingrestful.resources.QueryParamFiltersDTO;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.json.Json;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import com.cso.sikoling.abstraction.entity.security.oauth2.User;
import com.cso.sikoling.abstraction.service.KeyService;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikoling.abstraction.service.TokenService;
import com.cso.sikoling.abstraction.service.UserService;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.exception.UnspecifiedException;
import com.cso.sikolingrestful.resources.security.CredentialDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

@Stateless
@LocalBean
@Path("user")
public class UserResource {
    
    @Inject
    private UserService<User> userService;
    
    @Inject
    private TokenService<Token> tokenService;
    
    @Inject
    private Service<Autorisasi> autorisasiService;
    
    @Inject
    private KeyService<Key> keyService;  
    
    @Inject
    private Properties appProperties;
    
    @GET
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Produces({MediaType.APPLICATION_JSON})
    public List<UserDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return userService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new UserDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return userService.getDaftarData(null)
                        .stream()
                        .map(t -> new UserDTO(t))
                        .collect(Collectors.toList());
            }             
        } 
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
        
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public UserDTO save(CredentialDTO credentialDTO) throws SQLException {         
        try {            
            UserDTO userDTO = new UserDTO();
            userDTO.setUser_name(credentialDTO.getEmail());
            userDTO.setPassword(credentialDTO.getPassword());
            userDTO.setHashing_password_type_id("05");
            Date currentDate = new Date();
            userDTO.setTanggal_registrasi(currentDate);
            return new UserDTO(userService.save(userDTO.toUser()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json user harus disertakan di body post request");
        }  
    }
    
    @Path("/authentication")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public TokenDTO authentication(CredentialDTO credentialDTO) throws UnspecifiedException, SQLException { 
        Token token;
        User user = userService.authentication(credentialDTO.toCredential()); 
        
        if(user != null) {
            List<Filter> filterAutorisasi = new ArrayList<>();
            Filter filter = new Filter("id_user", user.getId());
            filterAutorisasi.add(filter);
            QueryParamFilters qFilterAutorisasi = new QueryParamFilters(false, null, filterAutorisasi, null);
            Autorisasi autorisasi = autorisasiService.getDaftarData(qFilterAutorisasi).getFirst();
            
            List<Filter> filterKey = new ArrayList<>();
            filter = new Filter("realm", appProperties.getProperty("ID_REALM"));
            filterKey.add(filter);
            filter = new Filter("jwa", appProperties.getProperty("ID_JWA"));
            filterKey.add(filter);
            filter = new Filter("id", appProperties.getProperty("ID_KEY"));
            filterKey.add(filter);
            QueryParamFilters qFilterKey = new QueryParamFilters(false, null, filterKey, null);
            
            Key key = keyService.getDaftarData(qFilterKey).getFirst();
            
            token = tokenService.generateToken(key, autorisasi);
            
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
        
//        JsonObject model = Json.createObjectBuilder()
//                    .add("status", userService.authentication(credentialDTO.toCredential()) != null ? "sukses" : "gagal")
//                    .build();
//            
//        return model;
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR, Role.UMUM})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public UserDTO update(@PathParam("idLama") String idLama, UserDTO userDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(userDTO.getId());
            if(isIdSame) {
                return new UserDTO(userService.update(userDTO.toUser()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru user harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json user harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public UserDTO updateId(@PathParam("idLama") String idLama, UserDTO userDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(userDTO.getId());

            if(!isIdSame) {
                return new UserDTO(userService.updateId(idLama, userDTO.toUser()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru user harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json user harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idUser}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idUser") String idUser) throws SQLException {
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", userService.delete(idUser) == true ? "sukses" : "gagal")
                    .build();
            
            return model;
    }
    
}
