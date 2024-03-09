package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.ResponsavelDTO;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Responsavel;
import br.ueg.prog.webi.api.model.IEntidade;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-09T13:29:37-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class ResponsavelMapperImpl implements ResponsavelMapper {

    @Override
    public List<ResponsavelDTO> toDTO(List<Responsavel> lista) {
        if ( lista == null ) {
            return null;
        }

        List<ResponsavelDTO> list = new ArrayList<ResponsavelDTO>( lista.size() );
        for ( Responsavel responsavel : lista ) {
            list.add( toDTO( responsavel ) );
        }

        return list;
    }

    @Override
    public void updateModel(Responsavel source, Responsavel target) {
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
        if ( source.getPessoa() != null ) {
            target.setPessoa( source.getPessoa() );
        }
        if ( source.getVinculo() != null ) {
            target.setVinculo( source.getVinculo() );
        }
        if ( source.getTutor() != null ) {
            target.setTutor( source.getTutor() );
        }
    }

    @Override
    public ResponsavelDTO toDTO(Responsavel modelo) {
        if ( modelo == null ) {
            return null;
        }

        ResponsavelDTO.ResponsavelDTOBuilder responsavelDTO = ResponsavelDTO.builder();

        responsavelDTO.nomeResponsavel( modeloPessoaNome( modelo ) );
        responsavelDTO.cpfResponsavel( modeloPessoaCpf( modelo ) );
        responsavelDTO.idMatricula( modeloMatriculaId( modelo ) );
        responsavelDTO.nomeMatricula( modeloMatriculaPessoaNome( modelo ) );
        responsavelDTO.vinculo( modelo.getVinculo() );
        responsavelDTO.tutor( modelo.getTutor() );

        return responsavelDTO.build();
    }

    @Override
    public Responsavel toModelo(ResponsavelDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Responsavel.ResponsavelBuilder responsavel = Responsavel.builder();

        responsavel.pessoa( responsavelDTOToPessoa( dto ) );
        responsavel.matricula( responsavelDTOToMatricula( dto ) );
        responsavel.vinculo( dto.getVinculo() );
        responsavel.tutor( dto.getTutor() );

        return responsavel.build();
    }

    private String modeloPessoaNome(Responsavel responsavel) {
        if ( responsavel == null ) {
            return null;
        }
        Pessoa pessoa = responsavel.getPessoa();
        if ( pessoa == null ) {
            return null;
        }
        String nome = pessoa.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }

    private String modeloPessoaCpf(Responsavel responsavel) {
        if ( responsavel == null ) {
            return null;
        }
        Pessoa pessoa = responsavel.getPessoa();
        if ( pessoa == null ) {
            return null;
        }
        String cpf = pessoa.getCpf();
        if ( cpf == null ) {
            return null;
        }
        return cpf;
    }

    private Long modeloMatriculaId(Responsavel responsavel) {
        if ( responsavel == null ) {
            return null;
        }
        Matricula matricula = responsavel.getMatricula();
        if ( matricula == null ) {
            return null;
        }
        Long id = matricula.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String modeloMatriculaPessoaNome(Responsavel responsavel) {
        if ( responsavel == null ) {
            return null;
        }
        Matricula matricula = responsavel.getMatricula();
        if ( matricula == null ) {
            return null;
        }
        Pessoa pessoa = matricula.getPessoa();
        if ( pessoa == null ) {
            return null;
        }
        String nome = pessoa.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }

    protected Pessoa responsavelDTOToPessoa(ResponsavelDTO responsavelDTO) {
        if ( responsavelDTO == null ) {
            return null;
        }

        Pessoa.PessoaBuilder pessoa = Pessoa.builder();

        pessoa.cpf( responsavelDTO.getCpfResponsavel() );

        return pessoa.build();
    }

    protected Matricula responsavelDTOToMatricula(ResponsavelDTO responsavelDTO) {
        if ( responsavelDTO == null ) {
            return null;
        }

        Matricula.MatriculaBuilder matricula = Matricula.builder();

        matricula.id( responsavelDTO.getIdMatricula() );

        return matricula.build();
    }
}
