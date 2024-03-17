package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.DocumentoMatriculaDTO;
import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.model.DocumentoMatricula;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentoMatriculaMapper extends BaseMapper<DocumentoMatricula, DocumentoMatriculaDTO> {

    @Override
    @Mapping(target = "matricula.id", source = "idMatricula")
    @Mapping(target = "idTipoDocumento", source = "tipoDocumento.id")
    DocumentoMatricula toModelo(DocumentoMatriculaDTO documentoMatriculaDTO);

    @Override
    @Mapping(target = "idMatricula", source = "matricula.id")
    @Mapping(target = "tipoDocumento", expression = "java(getTipoDocumentoById(modelo.getIdTipoDocumento()))")
    DocumentoMatriculaDTO toDTO(DocumentoMatricula modelo);

    default TipoDocumento getTipoDocumentoById(String idTipoDocumento){
        return TipoDocumento.getById(idTipoDocumento);
    }
}
