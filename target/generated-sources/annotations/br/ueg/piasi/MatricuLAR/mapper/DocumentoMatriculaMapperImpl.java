package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.DocumentoMatriculaDTO;
import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import br.ueg.piasi.MatricuLAR.model.DocumentoMatricula;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.prog.webi.api.model.IEntidade;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-28T16:05:56-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Amazon.com Inc.)"
)
@Component
public class DocumentoMatriculaMapperImpl implements DocumentoMatriculaMapper {

    @Override
    public List<DocumentoMatriculaDTO> toDTO(List<DocumentoMatricula> lista) {
        if ( lista == null ) {
            return null;
        }

        List<DocumentoMatriculaDTO> list = new ArrayList<DocumentoMatriculaDTO>( lista.size() );
        for ( DocumentoMatricula documentoMatricula : lista ) {
            list.add( toDTO( documentoMatricula ) );
        }

        return list;
    }

    @Override
    public void updateModel(DocumentoMatricula source, DocumentoMatricula target) {
        if ( source == null ) {
            return;
        }

        if ( source.getId() != null ) {
            target.setId( source.getId() );
        }
        if ( source.getCompositePkEntidadeObject() != null ) {
            target.setCompositePkEntidadeObject( source.getCompositePkEntidadeObject() );
        }
        if ( target.getForeignEntitiesMaps() != null ) {
            Map<String, IEntidade<?>> map = source.getForeignEntitiesMaps();
            if ( map != null ) {
                target.getForeignEntitiesMaps().clear();
                target.getForeignEntitiesMaps().putAll( map );
            }
        }
        else {
            Map<String, IEntidade<?>> map = source.getForeignEntitiesMaps();
            if ( map != null ) {
                target.setForeignEntitiesMaps( new LinkedHashMap<String, IEntidade<?>>( map ) );
            }
        }
        if ( source.getMatricula() != null ) {
            target.setMatricula( source.getMatricula() );
        }
        if ( source.getIdTipoDocumento() != null ) {
            target.setIdTipoDocumento( source.getIdTipoDocumento() );
        }
        if ( source.getAceito() != null ) {
            target.setAceito( source.getAceito() );
        }
        if ( source.getCaminhoDocumento() != null ) {
            target.setCaminhoDocumento( source.getCaminhoDocumento() );
        }
    }

    @Override
    public DocumentoMatricula toModelo(DocumentoMatriculaDTO documentoMatriculaDTO) {
        if ( documentoMatriculaDTO == null ) {
            return null;
        }

        DocumentoMatricula.DocumentoMatriculaBuilder documentoMatricula = DocumentoMatricula.builder();

        documentoMatricula.matricula( documentoMatriculaDTOToMatricula( documentoMatriculaDTO ) );
        documentoMatricula.idTipoDocumento( documentoMatriculaDTOTipoDocumentoId( documentoMatriculaDTO ) );
        documentoMatricula.aceito( documentoMatriculaDTO.getAceito() );
        documentoMatricula.caminhoDocumento( documentoMatriculaDTO.getCaminhoDocumento() );

        return documentoMatricula.build();
    }

    @Override
    public DocumentoMatriculaDTO toDTO(DocumentoMatricula modelo) {
        if ( modelo == null ) {
            return null;
        }

        DocumentoMatriculaDTO.DocumentoMatriculaDTOBuilder documentoMatriculaDTO = DocumentoMatriculaDTO.builder();

        documentoMatriculaDTO.idMatricula( modeloMatriculaId( modelo ) );
        documentoMatriculaDTO.aceito( modelo.getAceito() );
        documentoMatriculaDTO.caminhoDocumento( modelo.getCaminhoDocumento() );

        documentoMatriculaDTO.tipoDocumento( getTipoDocumentoById(modelo.getIdTipoDocumento()) );

        return documentoMatriculaDTO.build();
    }

    protected Matricula documentoMatriculaDTOToMatricula(DocumentoMatriculaDTO documentoMatriculaDTO) {
        if ( documentoMatriculaDTO == null ) {
            return null;
        }

        Matricula.MatriculaBuilder matricula = Matricula.builder();

        matricula.id( documentoMatriculaDTO.getIdMatricula() );

        return matricula.build();
    }

    private String documentoMatriculaDTOTipoDocumentoId(DocumentoMatriculaDTO documentoMatriculaDTO) {
        if ( documentoMatriculaDTO == null ) {
            return null;
        }
        TipoDocumento tipoDocumento = documentoMatriculaDTO.getTipoDocumento();
        if ( tipoDocumento == null ) {
            return null;
        }
        String id = tipoDocumento.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long modeloMatriculaId(DocumentoMatricula documentoMatricula) {
        if ( documentoMatricula == null ) {
            return null;
        }
        Matricula matricula = documentoMatricula.getMatricula();
        if ( matricula == null ) {
            return null;
        }
        Long id = matricula.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
