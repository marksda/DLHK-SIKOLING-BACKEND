package com.cso.sikolingrestful.provider;

import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.service.DAOTokenService;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import io.jsonwebtoken.Claims;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Provider
@Stateless
@LocalBean
@RequiredAuthorization
public class AuthorizationFilter implements ContainerRequestFilter {
    
    @Inject
    private DAOTokenService<Token> tokenService;
    
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        
        String authorizationHeader = Optional.ofNullable(crc.getHeaderString(HttpHeaders.AUTHORIZATION))
                                        .orElseThrow(() -> new NotAuthorizedException("Authorization header not found"));
        String accessToken = authorizationHeader.substring("Bearer".length()).trim();
        Claims claims = Optional.ofNullable(tokenService.validateAccessToken(accessToken))
                                .orElseThrow(() -> new NotAuthorizedException("Authorization header not found"));
        
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = Optional.ofNullable(extractRoles(resourceMethod))
                .orElseThrow(() -> new NotAuthorizedException("Role not found"));

        try {
            checkPermissions(methodRoles, claims);
        } catch (Exception e) {
            throw new NotAuthorizedException(e.toString());
        }
        
    }
    
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return null;
        } else {
            RequiredRole requiredRole = annotatedElement.getAnnotation(RequiredRole.class);
            if (requiredRole == null) {
                return null;
            } else {
                Role[] allowedRoles = requiredRole.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }
    
    private void checkPermissions(List<Role> allowedRoles, Claims claims) throws Exception {
        Iterator<Role> iterator = allowedRoles.iterator();          
        Set<String> audience = claims.getAudience();
        boolean allowRole = false;        
        Iterator iteratorAudience = audience.iterator();
        String roleId = null;
        
        if(iteratorAudience.hasNext()) {
            roleId = (String) iteratorAudience.next();
        }
        
        while(iterator.hasNext()) {    		
            Role role = iterator.next();
            if(role.label().equalsIgnoreCase(roleId)) {
                allowRole = true;
                break;
            }    		
    	}
        
        if(!allowRole) {
            throw new Exception("unauthorized Role");
    	}
    }

}
