package com.cso.sikolingrestful.resources.dokumen;

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
import com.cso.sikoling.abstraction.entity.dokumen.KategoriKbli;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.exception.UnspecifiedException;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("kategori_kbli")
public class KategoriKbliResource {
    
    @Inject
    private Service<KategoriKbli> kategoriKbliService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KategoriKbliDTO> getDaftarData(
            @QueryParam("filters") String queryParamsStr) throws UnspecifiedException {
        
        List<KategoriKbli> daftarKategoriKbli;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);
                daftarKategoriKbli = kategoriKbliService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarKategoriKbli == null) {
                    throw new UnspecifiedException(500, "daftar kategori kbli tidak ada");
                }
                else {
                    return daftarKategoriKbli
                            .stream()
                            .map(t -> new KategoriKbliDTO(t))
                            .collect(Collectors.toList());
                }                
            }
            else {
                daftarKategoriKbli = kategoriKbliService.getDaftarData(null);
                
                if(daftarKategoriKbli == null) {
                    throw new UnspecifiedException(500, "daftar kategori kbli tidak ada");
                }
                else {
                    return daftarKategoriKbli
                            .stream()
                            .map(t -> new KategoriKbliDTO(t))
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
    public KategoriKbliDTO save(KategoriKbliDTO kategoriKbliDTO) throws SQLException { 
        
        try {            
            return new KategoriKbliDTO(kategoriKbliService.save(kategoriKbliDTO.toKategoriKbli()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori kbli harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriKbliDTO update(@PathParam("idLama") String idLama, KategoriKbliDTO kategoriKbliDTO) throws SQLException {
        
        try {                
            if(idLama.equals(kategoriKbliDTO.getId())) {
                return new KategoriKbliDTO(kategoriKbliService.update(kategoriKbliDTO.toKategoriKbli()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru kategori kbli harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kategori kbli harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KategoriKbliDTO updateId(@PathParam("idLama") String idLama, 
            KategoriKbliDTO kategoriKbliDTO) throws SQLException {
        
        if(idLama.equals(kategoriKbliDTO.getId())) {
            return new KategoriKbliDTO(kategoriKbliService.updateId(idLama, kategoriKbliDTO.toKategoriKbli()));
        }
        else {
            throw new IllegalArgumentException("id lama dan baru kategori kbli harus beda");
        }
        
    } 
    
    @Path("/{idKategoriKbli}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKategoriKbli") String idKategoriKbli) throws SQLException {
            
        JsonObject model = Json.createObjectBuilder()
                .add("status", kategoriKbliService.delete(idKategoriKbli) == true ? "sukses" : "gagal")
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
                        kategoriKbliService.getJumlahData(
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
                    .add("jumlah", kategoriKbliService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
