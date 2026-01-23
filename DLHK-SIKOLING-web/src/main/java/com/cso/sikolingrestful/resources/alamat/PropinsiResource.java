package com.cso.sikolingrestful.resources.alamat;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.ws.rs.Path;
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
import com.cso.sikoling.abstraction.entity.alamat.Propinsi;
import com.cso.sikoling.abstraction.service.Service;
import com.cso.sikolingrestful.Role;
import com.cso.sikolingrestful.annotation.RequiredAuthorization;
import com.cso.sikolingrestful.annotation.RequiredRole;
import com.cso.sikolingrestful.exception.UnspecifiedException;
import com.cso.sikolingrestful.resources.QueryParamFiltersDTO;
import com.cso.sikolingrestful.resources.FilterDTO;
import java.util.ArrayList;

@Stateless
@LocalBean
@Path("propinsi")
public class PropinsiResource {
    
    @Inject
    private Service<Propinsi> propinsiService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PropinsiDTO> getDaftarData(
            @QueryParam("filters") String queryParamsStr) throws UnspecifiedException {
        
        List<Propinsi> daftarPropinsi;
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = 
                        jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                daftarPropinsi = 
                        propinsiService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters());
                
                if(daftarPropinsi == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarPropinsi
                        .stream()
                        .map(t -> new PropinsiDTO(t))
                        .collect(Collectors.toList());
                }                
            }
            else {
                daftarPropinsi = 
                        propinsiService.getDaftarData(null);
                
                if(daftarPropinsi == null) {
                    return new ArrayList<>();
                }
                else {
                    return daftarPropinsi
                            .stream()
                            .map(t -> new PropinsiDTO(t))
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
    public PropinsiDTO save(PropinsiDTO propinsiDTO) throws SQLException { 
        
        try {            
            return new PropinsiDTO(propinsiService.save(propinsiDTO.toPropinsi()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json propinsi harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PropinsiDTO update(@PathParam("idLama") String idLama, 
            PropinsiDTO propinsiDTO) throws SQLException, UnspecifiedException {
        
        try {                
            if(idLama.equals(propinsiDTO.getId())) {
                return new PropinsiDTO(propinsiService.update(propinsiDTO.toPropinsi()));
            }
            else {
                throw new UnspecifiedException(500, "Id baru tidak boleh sama dengan id baru");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json propinsi harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PropinsiDTO updateId(@PathParam("idLama") String idLama, 
            PropinsiDTO propinsiDTO) throws SQLException, UnspecifiedException {
                      
        if(idLama.equals(propinsiDTO.getId())) {
            throw new UnspecifiedException(500, "Id baru tidak boleh sama dengan id baru");
        }
        else {                
            return new PropinsiDTO(propinsiService.updateId(idLama, propinsiDTO.toPropinsi()));
        }
        
    } 
    
    @Path("/{idPropinsi}")
    @DELETE
    @RequiredAuthorization
    @RequiredRole({Role.ADMINISTRATOR})
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idPropinsi") String idPropinsi) throws SQLException {
        
        boolean isDigit = idPropinsi.matches("[0-9]+");
        
        if(isDigit) {		
            
            JsonObject model = Json.createObjectBuilder()
                    .add("status", propinsiService.delete(idPropinsi) == true ? "sukses" : "gagal")
                    .build();
            
            
            return model;
        }
        else {
            throw new IllegalArgumentException("id propinsi harus bilanagan panjang 2 digit");
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
                List<FilterDTO> filters = jsonb.fromJson(qfilters, 
                        new ArrayList<FilterDTO>(){}.getClass().getGenericSuperclass());
                
                JsonObject model = Json.createObjectBuilder()
                    .add(
                        "jumlah", 
                        propinsiService.getJumlahData(
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
                    .add("jumlah", propinsiService.getJumlahData(null))
                    .build();            
            
                return model;
            }
        }
        catch (JsonbException e) {
            throw new JsonbException("format query data json tidak sesuai");
        }
    }
    
}
