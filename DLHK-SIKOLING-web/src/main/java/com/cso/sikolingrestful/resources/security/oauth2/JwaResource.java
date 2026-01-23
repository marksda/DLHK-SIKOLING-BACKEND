package com.cso.sikolingrestful.resources.security.oauth2;

import com.cso.sikoling.abstraction.entity.security.oauth2.Jwa;
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
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("jwa")
public class JwaResource {
    
    @Inject
    private Service<Jwa> jwaService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<JwaDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        List<Jwa> daftarJwa;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = 
                        jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);
                daftarJwa = jwaService
                        .getDaftarData(queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarJwa == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarJwa
                        .stream()
                        .map(t -> new JwaDTO(t))
                        .collect(Collectors.toList());
                }
            }
            else {
                daftarJwa = jwaService.getDaftarData(null);
                
                if(daftarJwa == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarJwa
                        .stream()
                        .map(t -> new JwaDTO(t))
                        .collect(Collectors.toList());
                }
            }             
        } 
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
        
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JwaDTO save(JwaDTO jwaDTO) throws SQLException {         
        try {            
            return new JwaDTO(jwaService.save(jwaDTO.toJwa()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json jwa harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JwaDTO update(@PathParam("idLama") String idLama, JwaDTO jwaDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(jwaDTO.getId());
            if(isIdSame) {
                return new JwaDTO(jwaService.update(jwaDTO.toJwa()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru jwa harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json jwa harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JwaDTO updateId(@PathParam("idLama") String idLama, JwaDTO jwaDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(jwaDTO.getId());

            if(!isIdSame) {
                return new JwaDTO(jwaService.updateId(idLama, jwaDTO.toJwa()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru jwa harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json jwa harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idJwa}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idJwa") String idJwa) throws SQLException {            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", jwaService.delete(idJwa) == true ? "sukses" : "gagal")
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
                    .add("jumlah", 
                        jwaService.getJumlahData(
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
                    .add("jumlah", jwaService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
