package com.cso.sikolingrestful.provider;

import com.cso.sikolingrestful.exception.ExceptionResponse;
import com.cso.sikolingrestful.exception.KeyException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class KeyExceptionHandler implements ExceptionMapper<KeyException> {

    @Override
    public Response toResponse(KeyException e) {
        return Response.status(e.getErrorCode())
                .entity(new ExceptionResponse(e.getMessage(), "none"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
