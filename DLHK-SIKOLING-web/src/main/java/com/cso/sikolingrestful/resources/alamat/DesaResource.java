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
import com.cso.sikoling.abstraction.service.alamat.AlamatService;
import com.cso.sikoling.abstraction.entity.Desa;

@Stateless
@LocalBean
@Path("desa")
public class DesaResource {
    
    @Inject
    private AlamatService<Desa> desaService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<DesaDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return desaService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new DesaDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return desaService.getDaftarData(null)
                        .stream()
                        .map(t -> new DesaDTO(t))
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
    public DesaDTO save(DesaDTO desaDTO) throws SQLException { 
        
        try {            
            return new DesaDTO(desaService.save(desaDTO.toDesa()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json desa harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public DesaDTO update(@PathParam("idLama") String idLama, DesaDTO desaDTO) throws SQLException {
                
        boolean isDigit = idLama.matches("[0-9]+");

        if(isDigit) {
            try {                
                boolean isIdSame = idLama.equals(desaDTO.getId());
                if(isIdSame) {
                    return new DesaDTO(desaService.update(desaDTO.toDesa()));
                }
                else {
                    throw new IllegalArgumentException("id desa sama");
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("data json desa harus disertakan di body put request");
            }
        }
        else {
            throw new IllegalArgumentException("id desa harus bilangan panjang 10 digit");
        }  
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public DesaDTO updateId(@PathParam("idLama") String idLama, DesaDTO desaDTO) throws SQLException {
        
        boolean isDigit = idLama.matches("[0-9]+");

        if(isDigit) {
            try {                
                boolean isIdSame = idLama.equals(desaDTO.getId());
                
                if(!isIdSame) {
                    return new DesaDTO(desaService.updateId(idLama, desaDTO.toDesa()));
                }
                else {
                    throw new IllegalArgumentException("id desa sama");
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("data json desa harus disertakan di body put request");
            }
        }
        else {
            throw new IllegalArgumentException("id desa harus bilangan panjang 10 digit");
        }
        
    } 
    
    @Path("/{idDesa}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idDesa") String idDesa) throws SQLException {
        
        boolean isDigit = idDesa.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", desaService.delete(idDesa) == true ? "sukses" : "gagal")
                    .build();
            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id desa harus bilangan panjang 10 digit");
        }        
        
    }
    
}
