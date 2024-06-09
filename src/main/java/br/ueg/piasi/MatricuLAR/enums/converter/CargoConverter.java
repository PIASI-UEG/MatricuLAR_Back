package br.ueg.piasi.MatricuLAR.enums.converter;

import br.ueg.piasi.MatricuLAR.enums.Cargo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CargoConverter implements AttributeConverter<Cargo,String> {

    @Override
    public String convertToDatabaseColumn(Cargo status) {
        return status != null ? status.getId() : null;
    }

    @Override
    public Cargo convertToEntityAttribute(String id) {
        return Cargo.getById(id);
    }
}
