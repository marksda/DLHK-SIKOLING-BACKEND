package com.cso.sikolingrestful.resources.dokumen;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
import com.cso.sikolingrestful.resources.QueryParamFiltersDTO;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.json.Json;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import com.cso.sikoling.abstraction.entity.dokumen.Dokumen;
import com.cso.sikoling.abstraction.service.Service;

@Stateless
@LocalBean
@Path("dokumen")
public class DokumenResource {
    
    @Inject
    private Service<Dokumen> dokumenService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<DokumenDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return dokumenService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new DokumenDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return dokumenService.getDaftarData(null)
                        .stream()
                        .map(t -> new DokumenDTO(t))
                        .collect(Collectors.toList());
            }             
        } 
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
        
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public DokumenDTO save(DokumenDTO dokumenDTO) throws SQLException { 
        
        try {            
            return new DokumenDTO(dokumenService.save(dokumenDTO.toDokumen()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json dokumen harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public DokumenDTO update(@PathParam("idLama") String idLama, DokumenDTO dokumenDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(dokumenDTO.getId());
            if(isIdSame) {
                return new DokumenDTO(dokumenService.update(dokumenDTO.toDokumen()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru dokumen harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json dokumen harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public DokumenDTO updateId(@PathParam("idLama") String idLama, DokumenDTO dokumenDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(dokumenDTO.getId());

            if(!isIdSame) {
                return new DokumenDTO(dokumenService.updateId(idLama, dokumenDTO.toDokumen()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru dokumen harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json dokumen harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idDokumen}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idDokumen") String idDokumen) throws SQLException {
        
        boolean isDigit = idDokumen.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", dokumenService.delete(idDokumen) == true ? "sukses" : "gagal")
                    .build();
            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id dokumen harus bilangan panjang 2 digit");
        }        
        
    }
    
}
