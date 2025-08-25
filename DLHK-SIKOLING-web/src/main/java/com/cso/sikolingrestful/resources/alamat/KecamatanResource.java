package com.cso.sikolingrestful.resources.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
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
import com.cso.sikoling.abstraction.entity.alamat.Kecamatan;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("kecamatan")
public class KecamatanResource {
    
    @Inject
    private Service<Kecamatan> kecamatanService;
    
    @Inject
    private Service<Kabupaten> kabupatenService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KecamatanDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return kecamatanService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new KecamatanDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return kecamatanService.getDaftarData(null)
                        .stream()
                        .map(t -> new KecamatanDTO(t))
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
    public KecamatanDTO save(KecamatanDTO kecamatanDTO) throws SQLException { 
        List<Filter> fields_filter = new ArrayList<>();
        fields_filter.add(
            new Filter("id", kecamatanDTO.getId_kabupaten())
        );        
        QueryParamFilters queryParamFilters = new QueryParamFilters(
                                            false, null, fields_filter, null);        
                
        Kabupaten kabupaten = kabupatenService.getDaftarData(queryParamFilters)
                                              .getFirst();
        
        Kecamatan kecamatan = new Kecamatan(
            kecamatanDTO.getId(), 
            kecamatanDTO.getNama(), 
            kabupaten.getId_propinsi(), 
            kabupaten.getId()
        );
                
        try {            
            return new KecamatanDTO(kecamatanService.save(kecamatan));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kecamatan harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KecamatanDTO update(@PathParam("idLama") String idLama, KecamatanDTO kecamatanDTO) throws SQLException {
                
        boolean isDigit = idLama.matches("[0-9]+");

        if(isDigit) {
            try {                
                boolean isIdSame = idLama.equals(kecamatanDTO.getId());
                if(isIdSame) {
                    return new KecamatanDTO(kecamatanService.update(kecamatanDTO.toKecamatan()));
                }
                else {
                    throw new IllegalArgumentException("id lama dan baru kabupaten harus sama");
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("data json kecamatan harus disertakan di body put request");
            }
        }
        else {
            throw new IllegalArgumentException("id kecamatan harus bilangan panjang 7 digit");
        }  
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KecamatanDTO updateId(@PathParam("idLama") String idLama, KecamatanDTO kecamatanDTO) throws SQLException {
        boolean isDigit = idLama.matches("[0-9]+");

        if(isDigit) {
            try {                
                boolean isIdSame = idLama.equals(kecamatanDTO.getId());
                
                if(!isIdSame) {
                    return new KecamatanDTO(kecamatanService.updateId(idLama, kecamatanDTO.toKecamatan()));
                }
                else {
                    throw new IllegalArgumentException("id lama dan baru kabupaten harus beda");
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("data json kecamatan harus disertakan di body put request");
            }
        }
        else {
            throw new IllegalArgumentException("id kecamatan harus bilangan panjang 7 digit");
        }
        
    } 
    
    @Path("/{idKecamatan}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKecamatan") String idKecamatan) throws SQLException {
        
        boolean isDigit = idKecamatan.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", kecamatanService.delete(idKecamatan) == true ? "sukses" : "gagal")
                    .build();
            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id kecamatan harus bilangan panjang 7 digit");
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
                        kecamatanService.getJumlahData(
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
                    .add("jumlah", kecamatanService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
