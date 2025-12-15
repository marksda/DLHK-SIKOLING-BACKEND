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
import com.cso.sikoling.abstraction.entity.permohonan.PosisiTahapPemberkasan;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("posisi_tahap_pemberkasan")
public class PosisiTahapPemberkasanResource {
    
    @Inject
    private Service<PosisiTahapPemberkasan> posisiTahapPemberkasanService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PosisiTahapPemberkasanDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return posisiTahapPemberkasanService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new PosisiTahapPemberkasanDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return posisiTahapPemberkasanService.getDaftarData(null)
                        .stream()
                        .map(t -> new PosisiTahapPemberkasanDTO(t))
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
    public PosisiTahapPemberkasanDTO save(PosisiTahapPemberkasanDTO posisiTahapPemberkasanDTO) throws SQLException { 
        
        try {  
            posisiTahapPemberkasanDTO.setId(null);
            return new PosisiTahapPemberkasanDTO(posisiTahapPemberkasanService.save(posisiTahapPemberkasanDTO.toPosisiTahapPemberkasan()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json posisi tahap pemberkasan harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PosisiTahapPemberkasanDTO update(@PathParam("idLama") String idLama, PosisiTahapPemberkasanDTO posisiTahapPemberkasanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(posisiTahapPemberkasanDTO.getId());
            if(isIdSame) {
                return new PosisiTahapPemberkasanDTO(posisiTahapPemberkasanService.update(posisiTahapPemberkasanDTO.toPosisiTahapPemberkasan()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru posisi tahap permohonan harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json posisi tahap permohonan harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PosisiTahapPemberkasanDTO updateId(@PathParam("idLama") String idLama, PosisiTahapPemberkasanDTO posisiTahapPemberkasanDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(posisiTahapPemberkasanDTO.getId());

            if(!isIdSame) {
                return new PosisiTahapPemberkasanDTO(posisiTahapPemberkasanService.updateId(idLama, posisiTahapPemberkasanDTO.toPosisiTahapPemberkasan()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru posisi tahap permohonan harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json posisi tahap permohonan harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idPosisiTahapPermohonan}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idPosisiTahapPermohonan") String idPosisiTahapPermohonan) throws SQLException {
        
        boolean isDigit = idPosisiTahapPermohonan.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", posisiTahapPemberkasanService.delete(idPosisiTahapPermohonan) == true ? "sukses" : "gagal")
                    .build();            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id posisi tahap permohonan harus bilangan");
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
                        posisiTahapPemberkasanService.getJumlahData(
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
                    .add("jumlah", posisiTahapPemberkasanService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
