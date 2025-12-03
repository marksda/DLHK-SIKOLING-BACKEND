package com.cso.sikolingrestful.resources.security.oauth2;

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
import com.cso.sikoling.abstraction.entity.security.oauth2.JwaType;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("jwa_type")
public class JwaTypeResource {
    
    @Inject
    private Service<JwaType> jwaTypeService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<JwaTypeDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return jwaTypeService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new JwaTypeDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return jwaTypeService.getDaftarData(null)
                        .stream()
                        .map(t -> new JwaTypeDTO(t))
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
    public JwaTypeDTO save(JwaTypeDTO hakAksesDTO) throws SQLException { 
        
        try {            
            return new JwaTypeDTO(jwaTypeService.save(hakAksesDTO.toJwaType()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json jwa type harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JwaTypeDTO update(@PathParam("idLama") String idLama, JwaTypeDTO hakAksesDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(hakAksesDTO.getId());
            if(isIdSame) {
                return new JwaTypeDTO(jwaTypeService.update(hakAksesDTO.toJwaType()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru hashing password type harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json hashing password type harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JwaTypeDTO updateId(@PathParam("idLama") String idLama, JwaTypeDTO hakAksesDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(hakAksesDTO.getId());

            if(!isIdSame) {
                return new JwaTypeDTO(jwaTypeService.updateId(idLama, hakAksesDTO.toJwaType()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru hashing password type harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json hashing password type harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idJwaType}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idJwaType") String idJwaType) throws SQLException {
        
        boolean isDigit = idJwaType.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", jwaTypeService.delete(idJwaType) == true ? "sukses" : "gagal")
                    .build();            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id hashing password type harus bilangan panjang 1 digit");
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
                    .add("jumlah", 
                        jwaTypeService.getJumlahData(
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
                    .add("jumlah", jwaTypeService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
