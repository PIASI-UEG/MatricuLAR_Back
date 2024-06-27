package br.ueg.piasi.MatricuLAR.enums.converter;

import br.ueg.piasi.MatricuLAR.enums.Turno;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter(autoApply = true)
@Component()
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
