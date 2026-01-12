package com.cso.sikoling.main.repository.dokumen;

import com.cso.sikoling.abstraction.entity.dokumen.MetaFile;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MetaFileConverter implements AttributeConverter<MetaFile, String> {
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    public String convertToDatabaseColumn(MetaFile metaFile) {
        return jsonb.toJson(metaFile);
    }

    @Override
    public MetaFile convertToEntityAttribute(String dbData) {
        return jsonb.fromJson(dbData, MetaFile.class);
    }

}
