package com.cso.sikolingrestful.resources.permohonan;

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
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPengurusPermohonan;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("kategori_pengurus_permohonan")
public class KategoriPengurusPermohonanResource {
    
    @Inject
    private Service<KategoriPengurusPermohonan> kategoriPengurusPermohonanService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KategoriPengurusPermohonanDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        List<KategoriPengurusPermohonan> daftarKategoriPengurusPermohonan;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);
                daftarKategoriPengurusPermohonan = kategoriPengurusPermohonanService
                        .getDaftarData(queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarKategoriPengurusPermohonan == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarKategoriPengurusPermohonan
                        .stream()
                        .map(t -> new KategoriPengurusPermohonanDTO(t))
                        .collect(Collectors.toList());
                } 
            }
            else {
                daftarKategoriPengurusPermohonan = kategoriPengurusPermohonanService.getDaftarData(null);
                if(daftarKategoriPengurusPermohonan == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarKategoriPengurusPermohonan
                        .stream()
                        .map(t -> new KategoriPengurusPermohonanDTO(t))
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
    public KategoriPengurusPermohonanDTO save(KategoriPengurusPermohonanDTO kategoriPengurusPermohonanDTO) throws SQLException { 
        
        try {          
            kategoriPengurusPermohonanDTO.setId(null);
            return new KategoriPengurusPermohonanDTO(kategoriPengurusPermohonanService.save(kategoriPengurusPermohonanDTO.toKategoriPengurusPermohonan()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori pengurus permohonan harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriPengurusPermohonanDTO update(@PathParam("idLama") String idLama, KategoriPengurusPermohonanDTO kategoriPengurusPermohonanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(kategoriPengurusPermohonanDTO.getId());
            if(isIdSame) {
                return new KategoriPengurusPermohonanDTO(kategoriPengurusPermohonanService.update(kategoriPengurusPermohonanDTO.toKategoriPengurusPermohonan()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru kategori permohonan harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori pengurus permohonan harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriPengurusPermohonanDTO updateId(@PathParam("idLama") String idLama, KategoriPengurusPermohonanDTO kategoriPengurusPermohonanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(kategoriPengurusPermohonanDTO.getId());

            if(!isIdSame) {
                return new KategoriPengurusPermohonanDTO(kategoriPengurusPermohonanService.updateId(idLama, kategoriPengurusPermohonanDTO.toKategoriPengurusPermohonan()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru kategori permohonan harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori pengurus permohonan harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idKategoriPengurusPermohonan}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKategoriPengurusPermohonan") String idKategoriPengurusPermohonan) throws SQLException {
        
        boolean isDigit = idKategoriPengurusPermohonan.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", kategoriPengurusPermohonanService.delete(idKategoriPengurusPermohonan) == true ? "sukses" : "gagal")
                    .build();            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id kategori pengurus permohonan harus bilangan");
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
                        kategoriPengurusPermohonanService.getJumlahData(
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
                    .add("jumlah", kategoriPengurusPermohonanService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
