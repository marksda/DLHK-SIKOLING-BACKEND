package com.cso.sikolingrestful.resources.integrator;

import com.cso.sikoling.abstraction.entity.dokumen.MetaFile;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumen;
import com.cso.sikoling.abstraction.entity.dokumen.RegisterDokumenSementara;
import com.cso.sikoling.abstraction.entity.security.Otorisasi;
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
        String [] hasilSplit = file_id.split("_*_");
        MetaFile metaFile;

        if(hasilSplit.length > 1) {
            RegisterDokumenSementara registerDokumenSementara =
                    (RegisterDokumenSementara) crc.getProperty("registerDokumen");
            metaFile = registerDokumenSementara.getMetaFile();
        }
        else {
            RegisterDokumen registerDokumen = (RegisterDokumen) crc.getProperty("registerDokumen");
            metaFile = registerDokumen.getMetaFile();
            File tempFile;
                    
            if(metaFile == null) {
                String subPathLocation = File.separator
                        .concat(registerDokumen.getDokumen().getId());
                try {
                    tempFile = localStorageService
                            .download(registerDokumen.getNamaFile(), subPathLocation);
                } catch (IOException ex) {
                    tempFile = null;
                }
                
                Otorisasi otorisasi = (Otorisasi) crc.getProperty("otorisasi");
                
                metaFile = new MetaFile(
                        registerDokumen.getNamaFile(), 
                        registerDokumen.getPerusahaan().getId(), 
                        tempFile != null ? tempFile.length(): 0, 
                        otorisasi.getId_user(), 
                        otorisasi.getPerson().getNama()
                );
            }
        }

        JsonObject model = Json.createObjectBuilder()
                        .add("BaseFileName", metaFile.getBaseFileName())
                        .add("Size", metaFile.getSize())
                        .add("UserId", metaFile.getUserId())
                        .add("UserFriendlyName", metaFile.getUserFriendlyName())
                        .add("UserCanWrite", metaFile.isUserCanWrite())
                        .build();            

        return model;
    }
    
    @Path("/files/{file_id}/contents")
    @GET
    @WopiRequiredAuthorization
    @WopiResponseHeader
    public Response wopiGetFile(
            @PathParam("file_id") String file_id,
            @QueryParam("access_token") String accessToken,
            @Context ContainerRequestContext crc) throws UnspecifiedException {
        String [] hasilSplit = file_id.split("_*_");
        String subPathLocation;
        File tempFile;
        String fileType;
        String fileSize;
        
        try {
            if(hasilSplit.length > 1) {
                RegisterDokumenSementara registerDokumenSementara =
                        (RegisterDokumenSementara) crc.getProperty("registerDokumen");
                subPathLocation = File.separator
                        .concat(registerDokumenSementara.getIdJenisDokumen());
                tempFile = localStorageService
                        .download(registerDokumenSementara.getNamaFile(), subPathLocation);
                fileType = Files.probeContentType(tempFile.toPath());
                fileSize = String.valueOf(tempFile.length());
            }
            else {
                RegisterDokumen registerDokumen = (RegisterDokumen) crc.getProperty("registerDokumen");
                subPathLocation = File.separator
                        .concat(registerDokumen.getDokumen().getId());
                tempFile = localStorageService
                        .download(registerDokumen.getNamaFile(), subPathLocation);
                fileType = Files.probeContentType(tempFile.toPath());
                fileSize = String.valueOf(tempFile.length());
            }
                
            return Response.status( Response.Status.OK )
                            .header("Content-Length", fileSize)
                            .header("Content-Type", fileType)
                            .entity(tempFile)
                            .build();
            
        } catch (IOException ex) {
            throw new  UnspecifiedException(500, "Gagal meload file dokumen");
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
