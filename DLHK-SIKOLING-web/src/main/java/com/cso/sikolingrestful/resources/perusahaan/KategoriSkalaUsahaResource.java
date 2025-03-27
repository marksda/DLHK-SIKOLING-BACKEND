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
import com.cso.sikoling.abstraction.entity.perusahaan.KategoriSkalaUsaha;
import com.cso.sikoling.abstraction.service.DAOService;

@Stateless
@LocalBean
@Path("kategori_skala_usaha")
public class KategoriSkalaUsahaResource {
    
    @Inject
    private DAOService<KategoriSkalaUsaha> kategoriSkalaUsahaService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KategoriSkalaUsahaDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return kategoriSkalaUsahaService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new KategoriSkalaUsahaDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return kategoriSkalaUsahaService.getDaftarData(null)
                        .stream()
                        .map(t -> new KategoriSkalaUsahaDTO(t))
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
    public KategoriSkalaUsahaDTO save(KategoriSkalaUsahaDTO kategoriSkalaUsahaDTO) throws SQLException { 
        
        try {            
            return new KategoriSkalaUsahaDTO(kategoriSkalaUsahaService.save(kategoriSkalaUsahaDTO.toKategoriSkalaUsaha()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori skala usaha harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriSkalaUsahaDTO update(@PathParam("idLama") String idLama, KategoriSkalaUsahaDTO kategoriSkalaUsahaDTO) throws SQLException {
         
        try {                
            boolean isIdSame = idLama.equals(kategoriSkalaUsahaDTO.getId());
            if(isIdSame) {
                return new KategoriSkalaUsahaDTO(kategoriSkalaUsahaService.update(kategoriSkalaUsahaDTO.toKategoriSkalaUsaha()));
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
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriSkalaUsahaDTO updateId(@PathParam("idLama") String idLama, KategoriSkalaUsahaDTO kategoriSkalaUsahaDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(kategoriSkalaUsahaDTO.getId());

            if(!isIdSame) {
                return new KategoriSkalaUsahaDTO(kategoriSkalaUsahaService.updateId(idLama, kategoriSkalaUsahaDTO.toKategoriSkalaUsaha()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru kategori skala usaha harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori skala usaha harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idKategoriSkalaUsaha}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKategoriSkalaUsaha") String idKategoriSkalaUsaha) throws SQLException {
        
        JsonObject model = Json.createObjectBuilder()
                .add("status", kategoriSkalaUsahaService.delete(idKategoriSkalaUsaha) == true ? "sukses" : "gagal")
                .build();

        return model;
        
    }
    
}
