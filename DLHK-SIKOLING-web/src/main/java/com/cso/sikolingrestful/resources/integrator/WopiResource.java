package com.cso.sikolingrestful.resources.integrator;

import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumen;
import com.cso.sikolingrestful.annotation.WopiRequiredAuthorization;
import com.cso.sikolingrestful.annotation.WopiResponseHeader;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Stateless
@LocalBean
@Path("wopi")
public class WopiResource {    
    
    @Path("/files/{file_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @WopiResponseHeader
    public JsonObject wopiCheckFileInfo(
//            @Context HttpHeaders httpHeaders, 
            @PathParam("file_id") String file_id,
            @QueryParam("access_token") String accessToken) {        
//        MultivaluedMap<String, String> dataHeader = httpHeaders.getRequestHeaders();
        JsonObject model = Json.createObjectBuilder()
                    .add("BaseFileName", "test.txt")
                    .add("Size", 11)
                    .add("UserId", file_id)
                    .add("UserCanWrite", true)
                    .build();            
            
        return model;
    }
    
    @Path("/files/{file_id}/contents")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @WopiRequiredAuthorization
    @WopiResponseHeader
    public String wopiGetFile(
            @PathParam("file_id") String file_id,
            @QueryParam("access_token") String accessToken,
            @Context ContainerRequestContext crc) {
        RegisterDokumen registerDokumen = (RegisterDokumen) crc.getProperty("registerDokumen");
                
        return "Nama dokumen: " + registerDokumen.getNamaFile() + ", Id file: " + file_id + ", Access token: " + accessToken;
    }
    
    @Path("/files/{file_id}/contents")
    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @WopiResponseHeader
    public String wopiPutFile(@PathParam("file_id") String file_id) {
        return "Hello wopi!!";
    }
    
    @Path("/files/{file_id}")
    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @WopiResponseHeader
    public String wopiPutRelativeFileOrRenameFile(@PathParam("file_id") String file_id) {
        //jika request header X-WOPI-Override=PUT_FILE -> buat file baru
        //dengan nama yang ada pada request header X-WOPI-SuggestedTarget=nama file
        
        //jika request header X-WOPI-Override=RENAME_FILE -> rename file
        //dengan nama yang ada pada request header X-WOPI-RequestedName=nama file
        
        return "Hello wopi!!";
    }
    
}
