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
import com.cso.sikoling.abstraction.entity.alamat.Desa;
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
@Path("desa")
public class DesaResource {
    
    @Inject
    private Service<Desa> desaService;
    
    @Inject
    private Service<Kecamatan> kecamatanService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<DesaDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        List<Desa> daftarDesa;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                daftarDesa = desaService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarDesa == null) {
                    return null;
                }
                else {
                    return daftarDesa
                        .stream()
                        .map(t -> new DesaDTO(t))
                        .collect(Collectors.toList());
                }                
            }
            else {
                daftarDesa = desaService.getDaftarData(null);
                
                if(daftarDesa == null) {
                    return null;
                }
                else {
                    return daftarDesa
                        .stream()
                        .map(t -> new DesaDTO(t))
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
    public DesaDTO save(DesaDTO desaDTO) throws SQLException, UnspecifiedException { 
        List<Filter> fields_filter = new ArrayList<>();
        fields_filter.add(
            new Filter("id", desaDTO.getId_kecamatan())
        );        
        QueryParamFilters queryParamFilters = new QueryParamFilters(
                                            false, null, fields_filter, null);
        
        List<Kecamatan> daftarKecamatan = kecamatanService.getDaftarData(queryParamFilters);
        Kecamatan kecamatan;
        
        if(daftarKecamatan == null) {
            throw new UnspecifiedException(500, "Data kecamatan tidak ada");
        }
        else {
            kecamatan = daftarKecamatan.getFirst();
        }
        
        Desa desa = new Desa(
                desaDTO.getId(), 
                desaDTO.getNama(), 
                kecamatan.getId_propinsi(), 
                kecamatan.getId_kabupaten(), 
                kecamatan.getId()
            );
        
        try {            
            return new DesaDTO(desaService.save(desa));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json desa harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public DesaDTO update(@PathParam("idLama") String idLama, DesaDTO desaDTO) throws SQLException, UnspecifiedException {
        
        if(idLama.equals(desaDTO.getId())) {
            List<Filter> fields_filter = new ArrayList<>();
            fields_filter.add(
                new Filter("id", desaDTO.getId_kecamatan())
            );        
            QueryParamFilters queryParamFilters = new QueryParamFilters(
                                                        false, null, fields_filter, null);
            List<Kecamatan> daftarKecamatan = kecamatanService.getDaftarData(queryParamFilters);
            
            if(daftarKecamatan == null) {
                throw new UnspecifiedException(500, "Data kecamatan tidak ada");
            }
            else {
                Kecamatan kecamatan = daftarKecamatan.getFirst();
                Desa desa = new Desa(
                                desaDTO.getId(), 
                                desaDTO.getNama(), 
                                kecamatan.getId_propinsi(), 
                                kecamatan.getId_kabupaten(), 
                                kecamatan.getId()
                            );
                    
                return new DesaDTO(desaService.update(desa));
            }
        }
        else {
            throw new UnspecifiedException(500, "Id desa tidak sama");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public DesaDTO updateId(@PathParam("idLama") String idLama, 
                DesaDTO desaDTO) throws SQLException, UnspecifiedException {
        
        if(idLama.equals(desaDTO.getId())) {
            throw new UnspecifiedException(500, "Id baru tidak boleh sama dengan id baru");
        }
        else {
            List<Filter> fields_filter = new ArrayList<>();
            fields_filter.add(
                new Filter("id", desaDTO.getId_kecamatan())
            );        
            QueryParamFilters queryParamFilters = 
                    new QueryParamFilters(false, null, fields_filter, null);
            
            List<Kecamatan> daftarKecamatan = kecamatanService.getDaftarData(queryParamFilters);
            
            if(daftarKecamatan == null) {
                throw new UnspecifiedException(500, "Data kecamatan tidak ditemukan");
            }
            else {
                Kecamatan kecamatan = daftarKecamatan.getFirst();
                Desa desa = new Desa(
                        desaDTO.getId(), 
                        desaDTO.getNama(), 
                        kecamatan.getId_propinsi(), 
                        kecamatan.getId_kabupaten(), 
                        kecamatan.getId()
                    );

                return new DesaDTO(desaService.updateId(idLama, desa));
            }
        }
            
    } 
    
    @Path("/{idDesa}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idDesa") String idDesa) throws SQLException {
            
        JsonObject model = Json.createObjectBuilder()
                .add("status", desaService.delete(idDesa) == true ? "sukses" : "gagal")
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
                        desaService.getJumlahData(
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
                    .add("jumlah", desaService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
