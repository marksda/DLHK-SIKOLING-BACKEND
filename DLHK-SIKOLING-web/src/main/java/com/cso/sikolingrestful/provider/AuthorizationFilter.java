package com.cso.sikolingrestful.provider;

import com.cso.sikoling.abstraction.entity.security.oauth2.Token;
import com.cso.sikoling.abstraction.service.DAOTokenService;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
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
import java.util.Optional;

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
        final SecurityContext securityContext = crc.getSecurityContext();
        String authorizationHeader = Optional.ofNullable(crc.getHeaderString(HttpHeaders.AUTHORIZATION))
                                        .orElseThrow(() -> new NotAuthorizedException("Authorization header not found"));
        String accessToken = authorizationHeader.substring("Bearer".length()).trim();
        Claims claims = tokenService.validateAccessToken(accessToken);
        
        
    }

}
