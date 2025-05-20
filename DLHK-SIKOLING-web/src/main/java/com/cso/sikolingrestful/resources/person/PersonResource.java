package com.cso.sikolingrestful.resources.person;

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
import com.cso.sikoling.abstraction.entity.person.Person;
import com.cso.sikoling.abstraction.service.Service;

@Stateless
@LocalBean
@Path("person")
public class PersonResource {
    
    @Inject
    private Service<Person> personService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PersonDTO> getDaftarData(@QueryParam("filters") String queryParamsStr) {
        
        try {            
            if(queryParamsStr != null) {
                Jsonb jsonb = JsonbBuilder.create();
                QueryParamFiltersDTO queryParamFiltersDTO = jsonb.fromJson(queryParamsStr, QueryParamFiltersDTO.class);

                return personService.getDaftarData(queryParamFiltersDTO.toQueryParamFilters())
                        .stream()
                        .map(t -> new PersonDTO(t))
                        .collect(Collectors.toList());
            }
            else {
                return personService.getDaftarData(null)
                        .stream()
                        .map(t -> new PersonDTO(t))
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
    public PersonDTO save(PersonDTO personDTO) throws SQLException { 
        
        try {            
            return new PersonDTO(personService.save(personDTO.toPerson()));
        } 
        catch (NullPointerException e) {
            throw new IllegalArgumentException("data json person harus disertakan di body post request");
        }    
        
    }
    
    @Path("/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PersonDTO update(@PathParam("idLama") String idLama, PersonDTO personDTO) throws SQLException {
         
        try {                
            boolean isIdSame = idLama.equals(personDTO.getId());
            if(isIdSame) {
                return new PersonDTO(personService.update(personDTO.toPerson()));
            }
            else {
                throw new IllegalArgumentException("id person harus sama");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json person harus disertakan di body put request");
        }
        
    }
    
    @Path("/update_id/{idLama}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public PersonDTO updateId(@PathParam("idLama") String idLama, PersonDTO personDTO) throws SQLException {
        
        try {                
            boolean isIdSame = idLama.equals(personDTO.getId());

            if(!isIdSame) {
                return new PersonDTO(personService.updateId(idLama, personDTO.toPerson()));
            }
            else {
                throw new IllegalArgumentException("id lama dan baru person harus beda");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("data json person harus disertakan di body put request");
        }
        
    } 
    
    @Path("/{idPerson}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject delete(@PathParam("idPerson") String idPerson) throws SQLException {
        
        JsonObject model = Json.createObjectBuilder()
                .add("status", personService.delete(idPerson) == true ? "sukses" : "gagal")
                .build();

        return model;
        
    }
    
}
