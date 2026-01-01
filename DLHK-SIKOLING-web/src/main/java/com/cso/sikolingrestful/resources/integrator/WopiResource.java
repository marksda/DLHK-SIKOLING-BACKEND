package com.cso.sikolingrestful.resources.integrator;

import com.cso.sikolingrestful.annotation.WopiResponseHeader;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Stateless
@LocalBean
@Path("wopi")
public class WopiResource {
    
    @Path("/files/{id}/content")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String getTesWopi() {
        return "Hello wopi!!";
    }
    
    @Path("/files/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @WopiResponseHeader
    public JsonObject getCheckFileInfo() {
        JsonObject model = Json.createObjectBuilder()
                    .add("BaseFileName", "test.txt")
                    .add("Size", 11)
                    .build();            
            
        return model;
    }
}
