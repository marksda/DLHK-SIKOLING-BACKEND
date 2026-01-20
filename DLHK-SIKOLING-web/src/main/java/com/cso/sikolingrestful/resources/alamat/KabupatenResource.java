package com.cso.sikolingrestful.resources.alamat;

import com.cso.sikoling.abstraction.entity.Filter;
import com.cso.sikoling.abstraction.entity.QueryParamFilters;
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
import com.cso.sikoling.abstraction.entity.alamat.Kabupaten;
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.exception.UnspecifiedException;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("kabupaten")
public class KabupatenResource {
    
    @Inject
    private Service<Kabupaten> kabupatenService;
    
    @Inject
    private Service<Propinsi> propinsiService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<KabupatenDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) throws UnspecifiedException {
        
        List<Kabupaten> daftarKabupaten;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = 
                        jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);
                daftarKabupaten = kabupatenService
                        .getDaftarData(queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarKabupaten == null) {
                    throw new UnspecifiedException(500, "daftar Kabupate tidak ada");
                }
                else {
                    return daftarKabupaten.stream()
                                        .map(t -> new KabupatenDTO(t))
                                        .collect(Collectors.toList());
                }
            }
            else {
                daftarKabupaten = kabupatenService.getDaftarData(null);
                
                if(daftarKabupaten == null) {
                    throw new UnspecifiedException(500, "daftar Kabupate tidak ada");
                }
                else {
                    return daftarKabupaten.stream()
                                        .map(t -> new KabupatenDTO(t))
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
    public KabupatenDTO save(KabupatenDTO kabupatenDTO) throws SQLException, UnspecifiedException { 
        
        List<Filter> fields_filter = new ArrayList<>();
        fields_filter.add(
            new Filter("id", kabupatenDTO.getId_propinsi())
        );        
        QueryParamFilters queryParamFilters = new QueryParamFilters(
                                            false, null, fields_filter, null);
        
        List<Propinsi> daftarPropinsi = propinsiService.getDaftarData(queryParamFilters);        
        Propinsi propinsi;
        
        if(daftarPropinsi == null) {
            throw new UnspecifiedException(500, "Data propinsi tidak ada");
        }
        else {
            propinsi = daftarPropinsi.getFirst();
        }
        
        Kabupaten kabupaten = new Kabupaten(
                                kabupatenDTO.getId(), 
                                kabupatenDTO.getNama(), 
                                propinsi.getId()
                            );
        
        try {            
            return new KabupatenDTO(kabupatenService.save(kabupaten));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json kabupaten harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public KabupatenDTO update(@PathParam("idLama") String idLama, KabupatenDTO kabupatenDTO) throws SQLException, UnspecifiedException {
        if(idLama.equals(kabupatenDTO.getId())) {
            List<Filter> fields_filter = new ArrayList<>();
            fields_filter.add(
                new Filter("id", kabupatenDTO.getId_propinsi())
            );        
            QueryParamFilters queryParamFilters = new QueryParamFilters(
                                                        false, null, fields_filter, null);
            List<Propinsi> daftarPropinsi = propinsiService.getDaftarData(queryParamFilters);
            
            if(daftarPropinsi == null) {
                throw new UnspecifiedException(500, "Data kecamatan tidak ada");
            }
            else {
                Propinsi propinsi = daftarPropinsi.getFirst();
                Kabupaten kabupaten = new Kabupaten(
                                kabupatenDTO.getId(), 
                                kabupatenDTO.getNama(), 
                                propinsi.getId()
                            );
                    
                return new KabupatenDTO(kabupatenService.update(kabupaten));
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
    public KabupatenDTO updateId(@PathParam("idLama") String idLama, KabupatenDTO kabupatenDTO) throws SQLException, UnspecifiedException {
        
        if(idLama.equals(kabupatenDTO.getId())) {
            throw new UnspecifiedException(500, "Id baru tidak boleh sama dengan id baru");
        }
        else {
            List<Filter> fields_filter = new ArrayList<>();
            fields_filter.add(
                new Filter("id", kabupatenDTO.getId_propinsi())
            );        
            QueryParamFilters queryParamFilters = 
                    new QueryParamFilters(false, null, fields_filter, null);
            
            List<Propinsi> daftarPropinsi = propinsiService.getDaftarData(queryParamFilters);
            
            if(daftarPropinsi == null) {
                throw new UnspecifiedException(500, "Data kecamatan tidak ditemukan");
            }
            else {
                Propinsi propinsi = daftarPropinsi.getFirst();
                Kabupaten kabupaten = new Kabupaten(
                        kabupatenDTO.getId(), 
                        kabupatenDTO.getNama(),
                        propinsi.getId()
                    );

                return new KabupatenDTO(kabupatenService.updateId(idLama, kabupaten));
            }
        }
        
    } 
    
    @Path("/{idKabupaten}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idKabupaten") String idKabupaten) throws SQLException {
            
        JsonObject model = Json.createObjectBuilder()
                .add("status", kabupatenService.delete(idKabupaten) == true ? "sukses" : "gagal")
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
                        kabupatenService.getJumlahData(
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
                    .add("jumlah", kabupatenService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
