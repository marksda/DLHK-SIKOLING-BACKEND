package com.cso.sikolingrestful.resources.alamat;

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
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
import com.cso.sikoling.abstraction.service.DAOService;

@Stateless
@LocalBean
@Path("kabupaten")
public class KabupatenResource {
    
    @Inject
    private DAOService<Kabupaten> kabupatenService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KabupatenDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return kabupatenService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new KabupatenDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return kabupatenService.getDaftarData(null)
                        .stream()
                        .map(t -> new KabupatenDTO(t))
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
    public KabupatenDTO save(KabupatenDTO kabupatenDTO) throws SQLException { 
        
        try {            
            return new KabupatenDTO(kabupatenService.save(kabupatenDTO.toKabupaten()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kabupaten harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KabupatenDTO update(@PathParam("idLama") String idLama, KabupatenDTO kabupatenDTO) throws SQLException {
                
        boolean isDigit = idLama.matches("[0-9]+");

        if(isDigit) {
            try {                
                boolean isIdSame = idLama.equals(kabupatenDTO.getId());
                if(isIdSame) {
                    return new KabupatenDTO(kabupatenService.update(kabupatenDTO.toKabupaten()));
                }
                else {
                    throw new IllegalArgumentException("id lama dan baru kabupaten harus sama");
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("data json kabupaten harus disertakan di body put request");
            }
        }
        else {
            throw new IllegalArgumentException("id kabupaten harus bilangan panjang 4 digit");
        }  
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KabupatenDTO updateId(@PathParam("idLama") String idLama, KabupatenDTO kabupatenDTO) throws SQLException {
        boolean isDigit = idLama.matches("[0-9]+");

        if(isDigit) {
            try {                
                boolean isIdSame = idLama.equals(kabupatenDTO.getId());
                
                if(!isIdSame) {
                    return new KabupatenDTO(kabupatenService.updateId(idLama, kabupatenDTO.toKabupaten()));
                }
                else {
                    throw new IllegalArgumentException("id lama dan baru kabupaten harus beda");
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("data json kabupaten harus disertakan di body put request");
            }
        }
        else {
            throw new IllegalArgumentException("id kabupaten harus bilangan panjang 4 digit");
        }
        
    } 
    
    @Path("/{idKabupaten}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKabupaten") String idKabupaten) throws SQLException {
        
        boolean isDigit = idKabupaten.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", kabupatenService.delete(idKabupaten) == true ? "sukses" : "gagal")
                    .build();
            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id kabupaten harus bilangan panjang 4 digit");
        }        
        
    }
    
}
