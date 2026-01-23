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
import com.cso.sikoling.abstraction.entity.perusahaan.Jabatan;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("jabatan")
public class JabatanResource {
    
    @Inject
    private Service<Jabatan> jabatanService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<JabatanDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        List<Jabatan> daftarJabatan;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = 
                        jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);
                daftarJabatan = jabatanService.getDaftarData(
                        queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarJabatan == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarJabatan
                        .stream()
                        .map(t -> new JabatanDTO(t))
                        .collect(Collectors.toList());
                }
            }
            else {
                daftarJabatan = jabatanService.getDaftarData(null);
                
                if(daftarJabatan == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarJabatan
                        .stream()
                        .map(t -> new JabatanDTO(t))
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
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public JabatanDTO save(JabatanDTO jabatanDTO) throws SQLException { 
        
        try {            
            return new JabatanDTO(jabatanService.save(jabatanDTO.toJabatan()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json jabatan harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JabatanDTO update(@PathParam("idLama") String idLama, JabatanDTO jabatanDTO) throws SQLException {
         
        try {                
            boolean isIdSame = idLama.equals(jabatanDTO.getId());
            if(isIdSame) {
                return new JabatanDTO(jabatanService.update(jabatanDTO.toJabatan()));
            }
            else {
                throw new IllegalArgumentException("id jabatan harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json jabatan harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JabatanDTO updateId(@PathParam("idLama") String idLama, JabatanDTO jabatanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(jabatanDTO.getId());

            if(!isIdSame) {
                return new JabatanDTO(jabatanService.updateId(idLama, jabatanDTO.toJabatan()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru jabatan harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json jabatan harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idJabatan}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idJabatan") String idJabatan) throws SQLException {
        
        JsonObject model = Json.createObjectBuilder()
                .add("status", jabatanService.delete(idJabatan) == true ? "sukses" : "gagal")
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
                        jabatanService.getJumlahData(
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
                    .add("jumlah", jabatanService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
