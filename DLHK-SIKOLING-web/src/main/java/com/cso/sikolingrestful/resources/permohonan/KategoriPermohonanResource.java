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
import com.cso.sikoling.abstraction.entity.permohonan.KategoriPermohonan;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("kategori_permohonan")
public class KategoriPermohonanResource {
    
    @Inject
    private Service<KategoriPermohonan> kategoriPermohonanService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KategoriPermohonanDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return kategoriPermohonanService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new KategoriPermohonanDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return kategoriPermohonanService.getDaftarData(null)
                        .stream()
                        .map(t -> new KategoriPermohonanDTO(t))
                        .collect(Collectors.toList());
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
    public KategoriPermohonanDTO save(KategoriPermohonanDTO kategoriPermohonanDTO) throws SQLException { 
        
        try {  
            kategoriPermohonanDTO.setId(null);
            return new KategoriPermohonanDTO(kategoriPermohonanService.save(kategoriPermohonanDTO.toKategoriPermohonan()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategoriPermohonan harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriPermohonanDTO update(@PathParam("idLama") String idLama, KategoriPermohonanDTO kategoriPermohonanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(kategoriPermohonanDTO.getId());
            if(isIdSame) {
                return new KategoriPermohonanDTO(kategoriPermohonanService.update(kategoriPermohonanDTO.toKategoriPermohonan()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru kategori permohonan harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategoriPermohonan harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriPermohonanDTO updateId(@PathParam("idLama") String idLama, KategoriPermohonanDTO kategoriPermohonanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(kategoriPermohonanDTO.getId());

            if(!isIdSame) {
                return new KategoriPermohonanDTO(kategoriPermohonanService.updateId(idLama, kategoriPermohonanDTO.toKategoriPermohonan()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru kategori permohonan harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori permohonan harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idKategoriPermohonan}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKategoriPermohonan") String idKategoriPermohonan) throws SQLException {
        
        boolean isDigit = idKategoriPermohonan.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", kategoriPermohonanService.delete(idKategoriPermohonan) == true ? "sukses" : "gagal")
                    .build();            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id kategori permohonan harus bilanagan");
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
                    .add(
                        "jumlah", 
                        kategoriPermohonanService.getJumlahData(
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
                    .add("jumlah", kategoriPermohonanService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
