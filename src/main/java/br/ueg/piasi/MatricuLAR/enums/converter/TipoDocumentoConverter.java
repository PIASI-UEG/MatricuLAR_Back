package br.ueg.piasi.MatricuLAR.enums.converter;

import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoDocumentoConverter implements AttributeConverter<TipoDocumento, String> {
    @Override
    public String convertToDatabaseColumn(TipoDocumento tipoDocumento) {
        return tipoDocumento.getId();
    }

    @Override
    public TipoDocumento convertToEntityAttribute(String id) {
        return TipoDocumento.getById(id);
    }
}
