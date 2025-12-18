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
import com.cso.sikoling.abstraction.entity.perusahaan.Pegawai;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("pegawai")
public class PegawaiResource {
    
    @Inject
    private Service<Pegawai> pegawaiService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PegawaiDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return pegawaiService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new PegawaiDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return pegawaiService.getDaftarData(null)
                        .stream()
                        .map(t -> new PegawaiDTO(t))
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
    public PegawaiDTO save(PegawaiDTO pegawaiDTO) throws SQLException { 
        
        try { 
            pegawaiDTO.setId(null);
            return new PegawaiDTO(pegawaiService.save(pegawaiDTO.toPegawai()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json pegawai harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PegawaiDTO update(@PathParam("idLama") String idLama, PegawaiDTO pegawaiDTO) throws SQLException {
         
        try {                
            boolean isIdSame = idLama.equals(pegawaiDTO.getId());
            if(isIdSame) {
                return new PegawaiDTO(pegawaiService.update(pegawaiDTO.toPegawai()));
            }
            else {
                throw new IllegalArgumentException("id pegawai harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json pegawai harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PegawaiDTO updateId(@PathParam("idLama") String idLama, PegawaiDTO pegawaiDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(pegawaiDTO.getId());

            if(!isIdSame) {
                return new PegawaiDTO(pegawaiService.updateId(idLama, pegawaiDTO.toPegawai()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru pegawai harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json pegawai harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idPegawai}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idPegawai") String idPegawai) throws SQLException {
        
        JsonObject model = Json.createObjectBuilder()
                .add("status", pegawaiService.delete(idPegawai) == true ? "sukses" : "gagal")
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
                    .add("jumlah", 
                        pegawaiService.getJumlahData(
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
                    .add("jumlah", pegawaiService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
