package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.NecessidadeEspecialDTO;
import br.ueg.piasi.MatricuLAR.model.NecessidadeEspecial;
import br.ueg.prog.webi.api.model.IEntidade;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-27T12:08:24-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Amazon.com Inc.)"
)
@Component
public class NecessidadeEspecialMapperImpl implements NecessidadeEspecialMapper {

    @Override
    public NecessidadeEspecial toModelo(NecessidadeEspecialDTO dto) {
        if ( dto == null ) {
            return null;
        }

        NecessidadeEspecial.NecessidadeEspecialBuilder necessidadeEspecial = NecessidadeEspecial.builder();

        necessidadeEspecial.id( dto.getId() );
        necessidadeEspecial.titulo( dto.getTitulo() );
        necessidadeEspecial.observacoes( dto.getObservacoes() );

        return necessidadeEspecial.build();
    }

    @Override
    public NecessidadeEspecialDTO toDTO(NecessidadeEspecial modelo) {
        if ( modelo == null ) {
            return null;
        }

        NecessidadeEspecialDTO necessidadeEspecialDTO = new NecessidadeEspecialDTO();

        necessidadeEspecialDTO.setId( modelo.getId() );
        necessidadeEspecialDTO.setTitulo( modelo.getTitulo() );
        necessidadeEspecialDTO.setObservacoes( modelo.getObservacoes() );

        return necessidadeEspecialDTO;
    }

    @Override
    public List<NecessidadeEspecialDTO> toDTO(List<NecessidadeEspecial> lista) {
        if ( lista == null ) {
            return null;
        }

        List<NecessidadeEspecialDTO> list = new ArrayList<NecessidadeEspecialDTO>( lista.size() );
        for ( NecessidadeEspecial necessidadeEspecial : lista ) {
            list.add( toDTO( necessidadeEspecial ) );
        }

        return list;
    }

    @Override
    public void updateModel(NecessidadeEspecial source, NecessidadeEspecial target) {
        if ( source == null ) {
            return;
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
        if ( source.getId() != null ) {
            target.setId( source.getId() );
        }
        if ( source.getTitulo() != null ) {
            target.setTitulo( source.getTitulo() );
        }
        if ( source.getObservacoes() != null ) {
            target.setObservacoes( source.getObservacoes() );
        }
    }
}
