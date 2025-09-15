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
import com.cso.sikoling.abstraction.entity.dokumen.VersiKbli;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("versi_kbli")
public class VersiKbliResource {
    
    @Inject
    private Service<VersiKbli> versiKbliService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<VersiKbliDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return versiKbliService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new VersiKbliDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return versiKbliService.getDaftarData(null)
                        .stream()
                        .map(t -> new VersiKbliDTO(t))
                        .collect(Collectors.toList());
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
    public VersiKbliDTO save(VersiKbliDTO versiKbliDTO) throws SQLException { 
        
        try {            
            return new VersiKbliDTO(versiKbliService.save(versiKbliDTO.toVersiKbli()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json versi kbli harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public VersiKbliDTO update(@PathParam("idLama") String idLama, VersiKbliDTO versiKbliDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(versiKbliDTO.getId());
            if(isIdSame) {
                return new VersiKbliDTO(versiKbliService.update(versiKbliDTO.toVersiKbli()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru versi kbli harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json versi kbli harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public VersiKbliDTO updateId(@PathParam("idLama") String idLama, VersiKbliDTO versiKbliDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(versiKbliDTO.getId());

            if(!isIdSame) {
                return new VersiKbliDTO(versiKbliService.updateId(idLama, versiKbliDTO.toVersiKbli()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru versi kbli harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json versi kbli harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idVersiKbli}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idVersiKbli") String idVersiKbli) throws SQLException {
        
        boolean isDigit = idVersiKbli.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", versiKbliService.delete(idVersiKbli) == true ? "sukses" : "gagal")
                    .build();
            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id versi kbli harus bilangan panjang 2 digit");
        }        
        
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
                        versiKbliService.getJumlahData(
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
                    .add("jumlah", versiKbliService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
