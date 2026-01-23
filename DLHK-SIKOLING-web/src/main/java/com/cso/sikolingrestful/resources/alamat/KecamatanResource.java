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
import com.cso.sikolingrestful.exception.UnspecifiedException;
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
    public List<KecamatanDTO> getDaftarData(
                    @QueryParam("filters") String queryParamsStr) throws UnspecifiedException {
        
        List<Kecamatan> daftarKecamatan;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = 
                    jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);
                
                daftarKecamatan = kecamatanService.getDaftarData(
                                    queryParamFiltersDTO.toQueryParamFilters()
                                );
                
                if(daftarKecamatan == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarKecamatan
                                .stream()
                                .map(t -> new KecamatanDTO(t))
                                .collect(Collectors.toList());
                }                
            }
            else {
                daftarKecamatan = kecamatanService.getDaftarData(null);
                
                if(daftarKecamatan == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarKecamatan
                                .stream()
                                .map(t -> new KecamatanDTO(t))
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
    public KecamatanDTO save(KecamatanDTO kecamatanDTO) throws SQLException, UnspecifiedException { 
        
        List<Filter> fields_filter = new ArrayList<>();
        fields_filter.add(
            new Filter("id", kecamatanDTO.getId_kabupaten())
        );        
        QueryParamFilters queryParamFilters = new QueryParamFilters(
                                            false, null, fields_filter, null);        
        
        List<Kabupaten> daftarKabupaten = kabupatenService.getDaftarData(queryParamFilters);
        Kabupaten kabupaten;
        
        if(daftarKabupaten == null) {
            throw new UnspecifiedException(500, "Data kabupaten tidak ada");
        }
        else {
            kabupaten = daftarKabupaten.getFirst();
        }
        
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
    public KecamatanDTO update(@PathParam("idLama") String idLama, 
                            KecamatanDTO kecamatanDTO) throws SQLException, UnspecifiedException {
        
        if(idLama.equals(kecamatanDTO.getId())) {
            List<Filter> fields_filter = new ArrayList<>();
            fields_filter.add(
                new Filter("id", kecamatanDTO.getId_kabupaten())
            );        
            QueryParamFilters queryParamFilters = new QueryParamFilters(
                                                        false, null, fields_filter, null);
            List<Kabupaten> daftarKabupaten = kabupatenService.getDaftarData(queryParamFilters);
            
            if(daftarKabupaten == null) {
                throw new UnspecifiedException(500, "Data kabupaten tidak ada");
            }
            else {
                Kabupaten kabupaten = daftarKabupaten.getFirst();
                Kecamatan kecamatan = new Kecamatan(
                                kecamatanDTO.getId(), 
                                kecamatanDTO.getNama(), 
                                kabupaten.getId_propinsi(),
                                kabupaten.getId()
                            );
                    
                return new KecamatanDTO(kecamatanService.update(kecamatan));
            }
        }
        else {
            throw new UnspecifiedException(500, "Id baru tidak boleh sama dengan id baru");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KecamatanDTO updateId(@PathParam("idLama") String idLama, KecamatanDTO kecamatanDTO) throws SQLException, UnspecifiedException {
        
        if(idLama.equals(kecamatanDTO.getId())) {
            throw new UnspecifiedException(500, "Id baru tidak boleh sama dengan id baru");
        }
        else {
            List<Filter> fields_filter = new ArrayList<>();
            fields_filter.add(
                new Filter("id", kecamatanDTO.getId_kabupaten())
            );        
            QueryParamFilters queryParamFilters = 
                    new QueryParamFilters(false, null, fields_filter, null);
            
            List<Kabupaten> daftarKabupaten = kabupatenService.getDaftarData(queryParamFilters);
            
            if(daftarKabupaten == null) {
                throw new UnspecifiedException(500, "Data kabupaten tidak ditemukan");
            }
            else {
                Kabupaten kabupaten = daftarKabupaten.getFirst();
                Kecamatan kecamatan = new Kecamatan(
                        kecamatanDTO.getId(), 
                        kecamatanDTO.getNama(),
                        kabupaten.getId_propinsi(),
                        kabupaten.getId()
                    );

                return new KecamatanDTO(kecamatanService.updateId(idLama, kecamatan));
            }
        }
        
    } 
    
    @Path("/{idKecamatan}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKecamatan") String idKecamatan) throws SQLException {
            
        JsonObject model = Json.createObjectBuilder()
                .add("status", kecamatanService.delete(idKecamatan) == true ? "sukses" : "gagal")
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
