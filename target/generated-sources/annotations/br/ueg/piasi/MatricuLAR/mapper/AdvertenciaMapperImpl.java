package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.AdvertenciaDTO;
import br.ueg.piasi.MatricuLAR.model.Advertencia;
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
    date = "2024-05-15T14:27:27-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Amazon.com Inc.)"
)
@Component
public class AdvertenciaMapperImpl implements AdvertenciaMapper {

    @Override
    public List<AdvertenciaDTO> toDTO(List<Advertencia> lista) {
        if ( lista == null ) {
            return null;
        }

        List<AdvertenciaDTO> list = new ArrayList<AdvertenciaDTO>( lista.size() );
        for ( Advertencia advertencia : lista ) {
            list.add( toDTO( advertencia ) );
        }

        return list;
    }

    @Override
    public void updateModel(Advertencia source, Advertencia target) {
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
        if ( source.getNumero() != null ) {
            target.setNumero( source.getNumero() );
        }
        if ( source.getMatricula() != null ) {
            target.setMatricula( source.getMatricula() );
        }
        if ( source.getTitulo() != null ) {
            target.setTitulo( source.getTitulo() );
        }
        if ( source.getDescricao() != null ) {
            target.setDescricao( source.getDescricao() );
        }
    }

    @Override
    public Advertencia toModelo(AdvertenciaDTO DTO) {
        if ( DTO == null ) {
            return null;
        }

        Advertencia.AdvertenciaBuilder advertencia = Advertencia.builder();

        advertencia.matricula( advertenciaDTOToMatricula( DTO ) );
        advertencia.numero( DTO.getNumero() );
        advertencia.titulo( DTO.getTitulo() );
        advertencia.descricao( DTO.getDescricao() );

        return advertencia.build();
    }

    @Override
    public AdvertenciaDTO toDTO(Advertencia modelo) {
        if ( modelo == null ) {
            return null;
        }

        AdvertenciaDTO.AdvertenciaDTOBuilder advertenciaDTO = AdvertenciaDTO.builder();

        advertenciaDTO.idMatricula( modeloMatriculaId( modelo ) );
        advertenciaDTO.numero( modelo.getNumero() );
        advertenciaDTO.titulo( modelo.getTitulo() );
        advertenciaDTO.descricao( modelo.getDescricao() );

        return advertenciaDTO.build();
    }

    protected Matricula advertenciaDTOToMatricula(AdvertenciaDTO advertenciaDTO) {
        if ( advertenciaDTO == null ) {
            return null;
        }

        Matricula.MatriculaBuilder matricula = Matricula.builder();

        matricula.id( advertenciaDTO.getIdMatricula() );

        return matricula.build();
    }

    private Long modeloMatriculaId(Advertencia advertencia) {
        if ( advertencia == null ) {
            return null;
        }
        Matricula matricula = advertencia.getMatricula();
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
