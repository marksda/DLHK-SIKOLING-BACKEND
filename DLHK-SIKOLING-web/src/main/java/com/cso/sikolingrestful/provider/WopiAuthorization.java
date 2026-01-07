package com.cso.sikolingrestful.provider;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.service.Service;
import io.jsonwebtoken.Claims;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import com.cso.sikoling.abstraction.service.TokenService;
import com.cso.sikolingrestful.annotation.WopiRequiredAuthorization;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Provider
@Stateless
@LocalBean
@WopiRequiredAuthorization
public class WopiAuthorization implements ContainerRequestFilter {
    
    @Inject
    private TokenService<Token> tokenService;
    
    @Inject
    private Service<Otorisasi> otorisasiService;
    
//    @Context
//    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        UriInfo uriInfo = crc.getUriInfo();
        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
        String accessToken = Optional.ofNullable(queryParameters.getFirst("access_token"))
                                        .orElseThrow(() -> new NotAuthorizedException("Wopi authorization not found"));
        Claims claims = Optional.ofNullable(tokenService.validateAccessToken(accessToken))
                                .orElseThrow(() -> new NotAuthorizedException("Wopi authorization tidak valid"));
        
        try {
            List<Filter> fields_filter = new ArrayList<>();
            Filter filter = new Filter("id_user", claims.getAudience().stream().findFirst().orElseThrow());
            fields_filter.add(filter);
            QueryParamFilters qFilter = new QueryParamFilters(false, null, fields_filter, null);
        
            String idHakAkses = otorisasiService.getDaftarData(qFilter).getFirst().getHak_akses().getId();
//            Method resourceMethod = resourceInfo.getResourceMethod();
//            List<Role> methodRoles = Optional.ofNullable(extractRoles(resourceMethod))
//                                        .orElseThrow(() -> new NotAuthorizedException("Role not found"));
            try {
                checkPermissions(idHakAkses);
            } catch (Exception e) {
                throw new NotAuthorizedException(e.toString());
            }            
        } catch (NoSuchElementException e) {
            throw new NotAuthorizedException(e.toString());
        }
        
    }
    
//    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
//        if (annotatedElement == null) {
//            return null;
//        } else {
//            RequiredRole requiredRole = annotatedElement.getAnnotation(RequiredRole.class);
//            if (requiredRole == null) {
//                return null;
//            } else {
//                Role[] allowedRoles = requiredRole.value();
//                return Arrays.asList(allowedRoles);
//            }
//        }
//    }
    
    private void checkPermissions(String idHakAkses) throws Exception {
//        Iterator<Role> iterator = allowedRoles.iterator();   
        boolean allowRole = false; 
        
//        while(iterator.hasNext()) {    		
//            Role role = iterator.next();
//            if(role.label().equalsIgnoreCase(idHakAkses)) {
//                allowRole = true;
//                break;
//            }    		
//    	}
        
        if(!allowRole) {
            throw new Exception("unauthorized Role");
    	}
    }

}
