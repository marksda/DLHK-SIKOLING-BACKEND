package com.cso.sikolingrestful.resources.alamat;

import com.cso.sikoling.abstraction.service.alamat.PropinsiService;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@LocalBean
@Path("propinsi")
public class PropinsiResource {
    
    @Inject
    private PropinsiService propinsiService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PropinsiDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return propinsiService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new PropinsiDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return propinsiService.getDaftarData(null)
                        .stream()
                        .map(t -> new PropinsiDTO(t))
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
    public PropinsiDTO save(PropinsiDTO propinsiDTO) throws SQLException { 
        
        try {            
            return new PropinsiDTO(propinsiService.save(propinsiDTO.toPropinsi()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json propinsi harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PropinsiDTO update(@PathParam("idLama") String idLama, PropinsiDTO propinsiDTO) throws SQLException {
                
        boolean isDigit = idLama.matches("[0-9]+");

        if(isDigit) {
            try {                
                boolean isIdSame = idLama.equals(propinsiDTO.getId());
                if(isIdSame) {
                    return new PropinsiDTO(propinsiService.update(propinsiDTO.toPropinsi()));
                }
                else {
                    throw new IllegalArgumentException("id propinsi hasrus berbeda");
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("data json propinsi harus disertakan di body put request");
            }
        }
        else {
            throw new IllegalArgumentException("id propinsi harus digit");
        }  
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PropinsiDTO updateId(@PathParam("idLama") String idLama, PropinsiDTO propinsiDTO) throws SQLException {
        boolean isDigit = idLama.matches("[0-9]+");

        if(isDigit) {
            try {                
                boolean isIdSame = idLama.equals(propinsiDTO.getId());
                
                if(!isIdSame) {
                    return new PropinsiDTO(propinsiService.updateId(idLama, propinsiDTO.toPropinsi()));
                }
                else {
                    throw new IllegalArgumentException("id propinsi sama");
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("data json propinsi harus disertakan di body put request");
            }
        }
        else {
            throw new IllegalArgumentException("id propinsi harus digit");
        }
        
    } 
    
    @Path("/{idPropinsi}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idPropinsi") String idPropinsi) throws SQLException {
        
        boolean isDigit = idPropinsi.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", propinsiService.delete(idPropinsi) == true ? "sukses" : "gagal")
                    .build();
            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id propinsi harus digit");
        }        
        
    }
    
}
