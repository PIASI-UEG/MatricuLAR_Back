package br.ueg.piasi.MatricuLAR.enums.converter;

import br.ueg.piasi.MatricuLAR.enums.Vinculo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class VinculoConverter implements AttributeConverter<Vinculo, String> {

    @Override
    public String convertToDatabaseColumn(Vinculo vinculo) {
        return vinculo != null ?vinculo.getId() : null;
    }

    @Override
    public Vinculo convertToEntityAttribute(String id) {
        return Vinculo.getById(id);
    }
}

