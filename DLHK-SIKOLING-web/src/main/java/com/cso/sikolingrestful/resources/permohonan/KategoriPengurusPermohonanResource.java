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

@Stateless
@LocalBean
@Path("kategori_pengurus_permohonan")
public class KategoriPengurusPermohonanResource {
    
    @Inject
    private Service<KategoriPengurusPermohonan> kategoriPermohonanService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KategoriPengurusPermohonanDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return kategoriPermohonanService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new KategoriPengurusPermohonanDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return kategoriPermohonanService.getDaftarData(null)
                        .stream()
                        .map(t -> new KategoriPengurusPermohonanDTO(t))
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
    public KategoriPengurusPermohonanDTO save(KategoriPengurusPermohonanDTO kategoriPermohonanDTO) throws SQLException { 
        
        try {            
            return new KategoriPengurusPermohonanDTO(kategoriPermohonanService.save(kategoriPermohonanDTO.toKategoriPengurusPermohonan()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori pengurus permohonan harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriPengurusPermohonanDTO update(@PathParam("idLama") String idLama, KategoriPengurusPermohonanDTO kategoriPermohonanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(kategoriPermohonanDTO.getId());
            if(isIdSame) {
                return new KategoriPengurusPermohonanDTO(kategoriPermohonanService.update(kategoriPermohonanDTO.toKategoriPengurusPermohonan()));
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
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriPengurusPermohonanDTO updateId(@PathParam("idLama") String idLama, KategoriPengurusPermohonanDTO kategoriPermohonanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(kategoriPermohonanDTO.getId());

            if(!isIdSame) {
                return new KategoriPengurusPermohonanDTO(kategoriPermohonanService.updateId(idLama, kategoriPermohonanDTO.toKategoriPengurusPermohonan()));
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
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKategoriPengurusPermohonan") String idKategoriPengurusPermohonan) throws SQLException {
        
        boolean isDigit = idKategoriPengurusPermohonan.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", kategoriPermohonanService.delete(idKategoriPengurusPermohonan) == true ? "sukses" : "gagal")
                    .build();            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id kategori pengurus permohonan harus bilangan");
        }        
        
    }
    
}
