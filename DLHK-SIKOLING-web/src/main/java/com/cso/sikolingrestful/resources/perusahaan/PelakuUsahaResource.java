package com.cso.sikolingrestful.resources.perusahaan;

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
import com.cso.sikoling.abstraction.entity.perusahaan.PelakuUsaha;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("pelaku_usaha")
public class PelakuUsahaResource {
    
    @Inject
    private Service<PelakuUsaha> pelakuUsahaService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PelakuUsahaDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        List<PelakuUsaha> daftarPelakuUsaha;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = 
                        jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);
                daftarPelakuUsaha = pelakuUsahaService
                        .getDaftarData(queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarPelakuUsaha == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarPelakuUsaha
                        .stream()
                        .map(t -> new PelakuUsahaDTO(t))
                        .collect(Collectors.toList());
                }
            }
            else {
                daftarPelakuUsaha = pelakuUsahaService.getDaftarData(null);
                
                if(daftarPelakuUsaha == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarPelakuUsaha
                        .stream()
                        .map(t -> new PelakuUsahaDTO(t))
                        .collect(Collectors.toList());
                }
            }             
        } 
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
        
    }
    
    @POST
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PelakuUsahaDTO save(PelakuUsahaDTO pelakuUsahaDTO) throws SQLException { 
        
        try {            
            return new PelakuUsahaDTO(pelakuUsahaService.save(pelakuUsahaDTO.toPelakuUsaha()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json pelaku usaha harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PelakuUsahaDTO update(@PathParam("idLama") String idLama, PelakuUsahaDTO pelakuUsahaDTO) throws SQLException {
         
        try {                
            boolean isIdSame = idLama.equals(pelakuUsahaDTO.getId());
            if(isIdSame) {
                return new PelakuUsahaDTO(pelakuUsahaService.update(pelakuUsahaDTO.toPelakuUsaha()));
            }
            else {
                throw new IllegalArgumentException("id pelaku usaha harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json pelaku usaha harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PelakuUsahaDTO updateId(@PathParam("idLama") String idLama, PelakuUsahaDTO pelakuUsahaDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(pelakuUsahaDTO.getId());

            if(!isIdSame) {
                return new PelakuUsahaDTO(pelakuUsahaService.updateId(idLama, pelakuUsahaDTO.toPelakuUsaha()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru pelaku usaha harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json pelaku usaha harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idPelakuUsaha}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idPelakuUsaha") String idPelakuUsaha) throws SQLException {
        
        JsonObject model = Json.createObjectBuilder()
                .add("status", pelakuUsahaService.delete(idPelakuUsaha) == true ? "sukses" : "gagal")
                .build();

        return model;
        
    }
    
    @Path("/jumlah")
    @GET
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject getJumlahData(@QueryParam("filters") String qfilters) {
        try {
            if(qfilters != null) {
                Jsonb jsonb = JsonbBuilder.create();
                List<FilterDTO> filters = jsonb.fromJson(qfilters, new ArrayList<FilterDTO>(){}.getClass().getGenericSuperclass());
                
                JsonObject model = Json.createObjectBuilder()
                    .add(
                        "jumlah", 
                        pelakuUsahaService.getJumlahData(
                            filters
                                .stream()
                                .map(t -> t.toFilter())
                                .collect(Collectors.toList())
                        )
                    )
                    .build();            
            
                return model;
            }
            else {
                JsonObject model = Json.createObjectBuilder()
                    .add("jumlah", pelakuUsahaService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
