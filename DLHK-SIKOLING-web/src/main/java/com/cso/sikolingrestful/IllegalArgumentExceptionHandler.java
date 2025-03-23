package com.cso.sikolingrestful;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionHandler implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException e) {
        return Response.status(500)
                .entity(new ExceptionResponse(e.getMessage(), "none"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
