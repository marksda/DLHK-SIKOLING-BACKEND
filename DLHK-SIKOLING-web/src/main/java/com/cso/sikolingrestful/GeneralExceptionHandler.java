package com.cso.sikolingrestful;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        String msgError = e.getMessage();
        if(msgError != null) {
            msgError = msgError.replace("\"", "");
        }
        else {
            msgError = "format data tidak sesuai";
        }
        return Response.status(500)
                .entity(new ExceptionResponse(msgError, "none"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
