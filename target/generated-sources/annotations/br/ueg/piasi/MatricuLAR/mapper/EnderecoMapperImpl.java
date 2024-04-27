package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.EnderecoDTO;
import br.ueg.piasi.MatricuLAR.model.Endereco;
import br.ueg.prog.webi.api.model.IEntidade;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-27T19:06:12-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Amazon.com Inc.)"
)
@Component
public class EnderecoMapperImpl implements EnderecoMapper {

    @Override
    public Endereco toModelo(EnderecoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Endereco.EnderecoBuilder endereco = Endereco.builder();

        endereco.id( dto.getId() );
        endereco.cep( dto.getCep() );
        endereco.bairro( dto.getBairro() );
        endereco.cidade( dto.getCidade() );
        endereco.logradouro( dto.getLogradouro() );
        endereco.complemento( dto.getComplemento() );

        return endereco.build();
    }

    @Override
    public EnderecoDTO toDTO(Endereco modelo) {
        if ( modelo == null ) {
            return null;
        }

        EnderecoDTO.EnderecoDTOBuilder enderecoDTO = EnderecoDTO.builder();

        enderecoDTO.id( modelo.getId() );
        enderecoDTO.cep( modelo.getCep() );
        enderecoDTO.bairro( modelo.getBairro() );
        enderecoDTO.cidade( modelo.getCidade() );
        enderecoDTO.logradouro( modelo.getLogradouro() );
        enderecoDTO.complemento( modelo.getComplemento() );

        return enderecoDTO.build();
    }

    @Override
    public List<EnderecoDTO> toDTO(List<Endereco> lista) {
        if ( lista == null ) {
            return null;
        }

        List<EnderecoDTO> list = new ArrayList<EnderecoDTO>( lista.size() );
        for ( Endereco endereco : lista ) {
            list.add( toDTO( endereco ) );
        }

        return list;
    }

    @Override
    public void updateModel(Endereco source, Endereco target) {
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
        if ( source.getCep() != null ) {
            target.setCep( source.getCep() );
        }
        if ( source.getBairro() != null ) {
            target.setBairro( source.getBairro() );
        }
        if ( source.getCidade() != null ) {
            target.setCidade( source.getCidade() );
        }
        if ( source.getLogradouro() != null ) {
            target.setLogradouro( source.getLogradouro() );
        }
        if ( source.getComplemento() != null ) {
            target.setComplemento( source.getComplemento() );
        }
    }
}
