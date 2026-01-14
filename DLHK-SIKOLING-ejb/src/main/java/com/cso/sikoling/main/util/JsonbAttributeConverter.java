package com.cso.sikoling.main.util;

import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.SQLException;
import org.postgresql.util.PGobject;

@Converter(autoApply = true)
public class JsonbAttributeConverter implements AttributeConverter<JsonObject, PGobject> {

    @Override
    public PGobject convertToDatabaseColumn(JsonObject jsonObject) {
        if(jsonObject == null) return null;
        try {
            PGobject out = new PGobject();
            out.setType("jsonb");
            out.setValue(jsonObject.toString());
            return out;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Cannot convert " + jsonObject + " to JSON", ex);
        }
    }

    @Override
    public JsonObject convertToEntityAttribute(PGobject pGobject) {
        if (pGobject == null) return null;
        
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(pGobject.getValue(), JsonObject.class);
    }

}
