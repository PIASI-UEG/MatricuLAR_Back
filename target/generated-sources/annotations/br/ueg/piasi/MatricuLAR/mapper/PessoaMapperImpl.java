package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.PessoaDTO;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.prog.webi.api.model.IEntidade;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-17T19:26:55-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class PessoaMapperImpl implements PessoaMapper {

    @Override
    public Pessoa toModelo(PessoaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Pessoa.PessoaBuilder pessoa = Pessoa.builder();

        pessoa.cpf( dto.getCpf() );
        pessoa.nome( dto.getNome() );
        pessoa.telefone( dto.getTelefone() );

        return pessoa.build();
    }

    @Override
    public PessoaDTO toDTO(Pessoa modelo) {
        if ( modelo == null ) {
            return null;
        }

        PessoaDTO.PessoaDTOBuilder pessoaDTO = PessoaDTO.builder();

        pessoaDTO.cpf( modelo.getCpf() );
        pessoaDTO.nome( modelo.getNome() );
        pessoaDTO.telefone( modelo.getTelefone() );

        return pessoaDTO.build();
    }

    @Override
    public List<PessoaDTO> toDTO(List<Pessoa> lista) {
        if ( lista == null ) {
            return null;
        }

        List<PessoaDTO> list = new ArrayList<PessoaDTO>( lista.size() );
        for ( Pessoa pessoa : lista ) {
            list.add( toDTO( pessoa ) );
        }

        return list;
    }

    @Override
    public void updateModel(Pessoa source, Pessoa target) {
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
        if ( source.getCpf() != null ) {
            target.setCpf( source.getCpf() );
        }
        if ( source.getNome() != null ) {
            target.setNome( source.getNome() );
        }
        if ( source.getTelefone() != null ) {
            target.setTelefone( source.getTelefone() );
        }
    }
}
