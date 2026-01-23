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
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("kategori_model_perizinan")
public class KategoriModelPerizinanResource {
    
    @Inject
    private Service<KategoriModelPerizinan> kategoriModelPerizinanService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KategoriModelPerizinanDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        List<KategoriModelPerizinan> daftarKategoriModelPerizinan;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = 
                        jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);
                daftarKategoriModelPerizinan = 
                        kategoriModelPerizinanService.getDaftarData(
                                queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarKategoriModelPerizinan == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarKategoriModelPerizinan
                        .stream()
                        .map(t -> new KategoriModelPerizinanDTO(t))
                        .collect(Collectors.toList());
                }                
            }
            else {
                daftarKategoriModelPerizinan = kategoriModelPerizinanService.getDaftarData(null);
                
                if(daftarKategoriModelPerizinan == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarKategoriModelPerizinan
                        .stream()
                        .map(t -> new KategoriModelPerizinanDTO(t))
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
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
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
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
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
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKategoriModelPerizinan") String idKategoriModelPerizinan) throws SQLException {
        
        JsonObject model = Json.createObjectBuilder()
                .add("status", kategoriModelPerizinanService.delete(idKategoriModelPerizinan) == true ? "sukses" : "gagal")
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
                        kategoriModelPerizinanService.getJumlahData(
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
                    .add("jumlah", kategoriModelPerizinanService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
