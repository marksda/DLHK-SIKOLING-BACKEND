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
import com.cso.sikoling.abstraction.entity.perusahaan.Perusahaan;
import com.cso.sikoling.abstraction.service.DAOService;

@Stateless
@LocalBean
@Path("perusahaan")
public class PerusahaanResource {
    
    @Inject
    private DAOService<Perusahaan> perusahaanService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PerusahaanDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return perusahaanService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new PerusahaanDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return perusahaanService.getDaftarData(null)
                        .stream()
                        .map(t -> new PerusahaanDTO(t))
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
    public PerusahaanDTO save(PerusahaanDTO perusahaanDTO) throws SQLException { 
        
        try {            
            return new PerusahaanDTO(perusahaanService.save(perusahaanDTO.toPerusahaan()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json perusahaan harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PerusahaanDTO update(@PathParam("idLama") String idLama, PerusahaanDTO perusahaanDTO) throws SQLException {
         
        try {                
            boolean isIdSame = idLama.equals(perusahaanDTO.getId());
            if(isIdSame) {
                return new PerusahaanDTO(perusahaanService.update(perusahaanDTO.toPerusahaan()));
            }
            else {
                throw new IllegalArgumentException("id perusahaan harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json perusahaan harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PerusahaanDTO updateId(@PathParam("idLama") String idLama, PerusahaanDTO perusahaanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(perusahaanDTO.getId());

            if(!isIdSame) {
                return new PerusahaanDTO(perusahaanService.updateId(idLama, perusahaanDTO.toPerusahaan()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru perusahaan harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json perusahaan harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idPerusahaan}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idPerusahaan") String idPerusahaan) throws SQLException {
        
        JsonObject model = Json.createObjectBuilder()
                .add("status", perusahaanService.delete(idPerusahaan) == true ? "sukses" : "gagal")
                .build();

        return model;
        
    }
    
}
