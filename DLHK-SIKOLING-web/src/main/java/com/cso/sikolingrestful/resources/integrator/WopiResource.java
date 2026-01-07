package com.cso.sikolingrestful.resources.integrator;

import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumen;
import com.cso.sikoling.abstraction.service.LocalStorageService;
import com.cso.sikolingrestful.annotation.WopiRequiredAuthorization;
import com.cso.sikolingrestful.annotation.WopiResponseHeader;
import com.cso.sikolingrestful.exception.UnspecifiedException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
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
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Stateless
@LocalBean
@Path("wopi")
public class WopiResource {    
    
    @Inject
    private LocalStorageService localStorageService;
    
    @Path("/files/{file_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @WopiRequiredAuthorization
    @WopiResponseHeader
    public JsonObject wopiCheckFileInfo(
            @Context ContainerRequestContext crc, 
            @PathParam("file_id") String file_id) throws UnspecifiedException {        
        try {
            RegisterDokumen registerDokumen = (RegisterDokumen) crc.getProperty("registerDokumen");
            String subPathLocation = File.separator
                            .concat(registerDokumen.getDokumen().getId());
            File tempFile = localStorageService
                    .download(registerDokumen.getNamaFile(), subPathLocation);
            String fileSize = String.valueOf(tempFile.length());
            JsonObject model = Json.createObjectBuilder()
                        .add("BaseFileName", registerDokumen.getNamaFile())
                        .add("Size", fileSize)
                        .add("UserId", file_id)
                        .add("UserCanWrite", true)
                        .build();            

            return model;
        } catch (IOException ex) {
            throw new  UnspecifiedException(500, "id tidak dikenali sistem");
        }
    }
    
    @Path("/files/{file_id}/contents")
    @GET
    @WopiRequiredAuthorization
    @WopiResponseHeader
    public Response wopiGetFile(
            @PathParam("file_id") String file_id,
            @QueryParam("access_token") String accessToken,
            @Context ContainerRequestContext crc) throws UnspecifiedException {
        RegisterDokumen registerDokumen = (RegisterDokumen) crc.getProperty("registerDokumen");
                
        try {
            if(registerDokumen != null) {
                String subPathLocation = File.separator
                        .concat(registerDokumen.getDokumen().getId());
                File tempFile = localStorageService
                        .download(registerDokumen.getNamaFile(), subPathLocation);
                String fileType = Files.probeContentType(tempFile.toPath());
                String fileSize = String.valueOf(tempFile.length());
                
                return Response.status( Response.Status.OK )
                                .header("Content-Length", fileSize)
                                .header("Content-Type", fileType)
                                .entity(tempFile)
                                .build();
            }
            else {
                throw new UnspecifiedException(500, "id tidak dikenali sistem");
            }
        } catch (IOException ex) {
            throw new  UnspecifiedException(500, "id tidak dikenali sistem");
        }
        
        
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
