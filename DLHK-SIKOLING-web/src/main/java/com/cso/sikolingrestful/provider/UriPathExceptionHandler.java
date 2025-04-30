package com.cso.sikolingrestful.provider;

import com.cso.sikolingrestful.ExceptionResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import javax.xml.crypto.URIReferenceException;

@Provider
public class UriPathExceptionHandler implements ExceptionMapper<URIReferenceException> {

    @Override
    public Response toResponse(URIReferenceException e) {
        
        return Response.status(500)
                .entity(new ExceptionResponse(e.getMessage(), "none"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
