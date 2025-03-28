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
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriModelPerizinan;
import com.cso.sikoling.abstraction.service.DAOService;

@Stateless
@LocalBean
@Path("kategori_model_perizinan")
public class KategoriModelPerizinanResource {
    
    @Inject
    private DAOService<KategoriModelPerizinan> kategoriModelPerizinanService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KategoriModelPerizinanDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return kategoriModelPerizinanService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new KategoriModelPerizinanDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return kategoriModelPerizinanService.getDaftarData(null)
                        .stream()
                        .map(t -> new KategoriModelPerizinanDTO(t))
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
    public KategoriModelPerizinanDTO save(KategoriModelPerizinanDTO kategoriModelPerizinanDTO) throws SQLException { 
        
        try {            
            return new KategoriModelPerizinanDTO(kategoriModelPerizinanService.save(kategoriModelPerizinanDTO.toKategoriModelPerizinan()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori model perizinan harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriModelPerizinanDTO update(@PathParam("idLama") String idLama, KategoriModelPerizinanDTO kategoriModelPerizinanDTO) throws SQLException {
         
        try {                
            boolean isIdSame = idLama.equals(kategoriModelPerizinanDTO.getId());
            if(isIdSame) {
                return new KategoriModelPerizinanDTO(kategoriModelPerizinanService.update(kategoriModelPerizinanDTO.toKategoriModelPerizinan()));
            }
            else {
                throw new IllegalArgumentException("id kategori model perizinan harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori model perizinan harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriModelPerizinanDTO updateId(@PathParam("idLama") String idLama, KategoriModelPerizinanDTO kategoriModelPerizinanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(kategoriModelPerizinanDTO.getId());

            if(!isIdSame) {
                return new KategoriModelPerizinanDTO(kategoriModelPerizinanService.updateId(idLama, kategoriModelPerizinanDTO.toKategoriModelPerizinan()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru kategori model perizinan harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori model perizinan harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idKategoriModelPerizinan}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKategoriModelPerizinan") String idKategoriModelPerizinan) throws SQLException {
        
        JsonObject model = Json.createObjectBuilder()
                .add("status", kategoriModelPerizinanService.delete(idKategoriModelPerizinan) == true ? "sukses" : "gagal")
                .build();

        return model;
        
    }
    
}
