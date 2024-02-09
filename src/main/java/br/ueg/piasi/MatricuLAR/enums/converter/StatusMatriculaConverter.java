package br.ueg.piasi.MatricuLAR.enums.converter;

import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusMatriculaConverter implements AttributeConverter<StatusMatricula, String> {

    @Override
    public String convertToDatabaseColumn(StatusMatricula status) {
        return status != null ? status.getId() : null;
    }

    @Override
    public StatusMatricula convertToEntityAttribute(String id) {
        return StatusMatricula.getById(id);
    }
}