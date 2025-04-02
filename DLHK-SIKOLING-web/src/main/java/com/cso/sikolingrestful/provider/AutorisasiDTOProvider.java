package com.cso.sikolingrestful.provider;

import jakarta.ws.rs.ext.MessageBodyReader;
import com.cso.sikolingrestful.resources.security.AutorisasiDTO;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Consumes("application/json")
public class AutorisasiDTOProvider implements MessageBodyReader<AutorisasiDTO> {

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return type == AutorisasiDTO.class;
    }

    @Override
    public AutorisasiDTO readFrom(Class<AutorisasiDTO> type, Type type1, 
            Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, 
            InputStream in) throws IOException, WebApplicationException {
        
        AutorisasiDTO autorisasiDTO;
        
        try {
            Jsonb jsonb = JsonbBuilder.create();
            JsonObject d = jsonb.fromJson(in, JsonObject.class);
            autorisasiDTO = new AutorisasiDTO();
            autorisasiDTO.setId(d.getString("id"));
            autorisasiDTO.setId_hak_akses(d.getString("id_hak_akses"));
            autorisasiDTO.setId_lama(d.getString("id_lama", null));
            autorisasiDTO.setId_person(d.getString("id_person"));
            autorisasiDTO.setIs_verified(d.getBoolean("is_verified"));
            autorisasiDTO.setStatus_internal(d.getBoolean("status_internal"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(d.getString("tanggal_registrasi"));
            autorisasiDTO.setTanggal_registrasi(date);
            autorisasiDTO.setUser_name(d.getString("user_name"));
            
            return autorisasiDTO;
        } catch (JsonbException | ParseException e) {
            throw new IllegalArgumentException("parsing data json autorisasi terkendala");
        }
        
        
    }

}
