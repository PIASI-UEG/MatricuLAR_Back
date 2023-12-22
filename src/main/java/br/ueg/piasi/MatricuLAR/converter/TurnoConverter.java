package br.ueg.piasi.MatricuLAR.converter;

import br.ueg.piasi.MatricuLAR.enums.Turno;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TurnoConverter implements AttributeConverter<Turno, String> {

    @Override
    public String convertToDatabaseColumn(Turno turno) {
        return turno != null ? turno.getId() : null;
    }

    @Override
    public Turno convertToEntityAttribute(String id) {
        return Turno.getById(id);
    }
}
