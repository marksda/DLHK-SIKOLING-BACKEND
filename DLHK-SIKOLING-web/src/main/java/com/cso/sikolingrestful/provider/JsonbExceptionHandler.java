package com.cso.sikolingrestful.provider;

import com.cso.sikolingrestful.ExceptionResponse;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JsonbExceptionHandler implements ExceptionMapper<JsonbException> {

    @Override
    public Response toResponse(JsonbException e) {
        return Response.status(500)
                .entity(new ExceptionResponse(e.getMessage(), "none"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
