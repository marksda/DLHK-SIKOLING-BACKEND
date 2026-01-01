package com.cso.sikolingrestful.provider;

import com.cso.sikolingrestful.annotation.WopiResponseHeader;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Stateless
@LocalBean
@WopiResponseHeader
public class WopiFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, 
            ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("X-WOPI-HostEndpoint", "/api-sikoling/wopi/");
        responseContext.getHeaders().add("X-WOPI-MachineName", "Hokage");
        responseContext.getHeaders().add("X-WOPI-ServerVersion", "B.201");
    }

}
