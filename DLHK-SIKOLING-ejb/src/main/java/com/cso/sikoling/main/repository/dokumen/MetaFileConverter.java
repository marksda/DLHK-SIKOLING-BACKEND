package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.dokumen.MetaFile;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.SQLException;
import org.postgresql.util.PGobject;

@Converter(autoApply = true)
public class MetaFileConverter implements AttributeConverter<MetaFile, PGobject> {
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    public PGobject convertToDatabaseColumn(MetaFile metaFile) {
        if (metaFile == null) return null;
        try {
            PGobject out = new PGobject();
            out.setType("jsonb");
            out.setValue(jsonb.toJson(metaFile));
            return out;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Cannot convert " + metaFile + " to JSON", ex);
        }
    }

    @Override
    public MetaFile convertToEntityAttribute(PGobject dbData) {
        if (dbData == null) return null;
        return jsonb.fromJson(dbData.getValue(), MetaFile.class);
    }

}
