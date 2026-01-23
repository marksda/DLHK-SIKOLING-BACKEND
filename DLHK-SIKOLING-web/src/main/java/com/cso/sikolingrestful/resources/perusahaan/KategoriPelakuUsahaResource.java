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
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriPelakuUsaha;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("kategori_pelaku_usaha")
public class KategoriPelakuUsahaResource {
    
    @Inject
    private Service<KategoriPelakuUsaha> kategoriPelakuUsahaService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KategoriPelakuUsahaDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        List<KategoriPelakuUsaha> daftarKategoriPelakuUsaha;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = 
                        jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);
                daftarKategoriPelakuUsaha = kategoriPelakuUsahaService
                        .getDaftarData(queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarKategoriPelakuUsaha == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarKategoriPelakuUsaha
                        .stream()
                        .map(t -> new KategoriPelakuUsahaDTO(t))
                        .collect(Collectors.toList());
                }
            }
            else {
                daftarKategoriPelakuUsaha = kategoriPelakuUsahaService.getDaftarData(null);
                
                if(daftarKategoriPelakuUsaha == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarKategoriPelakuUsaha
                        .stream()
                        .map(t -> new KategoriPelakuUsahaDTO(t))
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
    public KategoriPelakuUsahaDTO save(KategoriPelakuUsahaDTO kategoriPelakuUsahaDTO) throws SQLException { 
        
        try {            
            return new KategoriPelakuUsahaDTO(kategoriPelakuUsahaService.save(kategoriPelakuUsahaDTO.toKategoriPelakuUsaha()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori skala usaha harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriPelakuUsahaDTO update(@PathParam("idLama") String idLama, KategoriPelakuUsahaDTO kategoriPelakuUsahaDTO) throws SQLException {
         
        try {                
            boolean isIdSame = idLama.equals(kategoriPelakuUsahaDTO.getId());
            if(isIdSame) {
                return new KategoriPelakuUsahaDTO(kategoriPelakuUsahaService.update(kategoriPelakuUsahaDTO.toKategoriPelakuUsaha()));
            }
            else {
                throw new IllegalArgumentException("id kategori skala usaha harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori skala usaha harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriPelakuUsahaDTO updateId(@PathParam("idLama") String idLama, KategoriPelakuUsahaDTO kategoriPelakuUsahaDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(kategoriPelakuUsahaDTO.getId());

            if(!isIdSame) {
                return new KategoriPelakuUsahaDTO(kategoriPelakuUsahaService.updateId(idLama, kategoriPelakuUsahaDTO.toKategoriPelakuUsaha()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru kategori skala usaha harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori skala usaha harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idKategoriPelakuUsaha}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKategoriPelakuUsaha") String idKategoriPelakuUsaha) throws SQLException {
        
        JsonObject model = Json.createObjectBuilder()
                .add("status", kategoriPelakuUsahaService.delete(idKategoriPelakuUsaha) == true ? "sukses" : "gagal")
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
                        kategoriPelakuUsahaService.getJumlahData(
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
                    .add("jumlah", kategoriPelakuUsahaService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
