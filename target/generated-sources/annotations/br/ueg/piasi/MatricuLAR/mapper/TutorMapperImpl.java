package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.TutorDTO;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Tutor;
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
public class TutorMapperImpl implements TutorMapper {

    @Override
    public List<TutorDTO> toDTO(List<Tutor> lista) {
        if ( lista == null ) {
            return null;
        }

        List<TutorDTO> list = new ArrayList<TutorDTO>( lista.size() );
        for ( Tutor tutor : lista ) {
            list.add( toDTO( tutor ) );
        }

        return list;
    }

    @Override
    public void updateModel(Tutor source, Tutor target) {
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
        if ( source.getPessoa() != null ) {
            target.setPessoa( source.getPessoa() );
        }
        if ( source.getEmpresaTelefone() != null ) {
            target.setEmpresaTelefone( source.getEmpresaTelefone() );
        }
        if ( source.getEmpresaCnpj() != null ) {
            target.setEmpresaCnpj( source.getEmpresaCnpj() );
        }
        if ( source.getEmpresaNome() != null ) {
            target.setEmpresaNome( source.getEmpresaNome() );
        }
        if ( source.getProfissao() != null ) {
            target.setProfissao( source.getProfissao() );
        }
        if ( source.getTelefoneWhatsapp() != null ) {
            target.setTelefoneWhatsapp( source.getTelefoneWhatsapp() );
        }
        if ( source.getVinculo() != null ) {
            target.setVinculo( source.getVinculo() );
        }
    }

    @Override
    public Tutor toModelo(TutorDTO tutorDTO) {
        if ( tutorDTO == null ) {
            return null;
        }

        Tutor.TutorBuilder tutor = Tutor.builder();

        tutor.pessoa( tutorDTOToPessoa( tutorDTO ) );
        tutor.cpf( tutorDTO.getCpf() );
        tutor.empresaTelefone( tutorDTO.getEmpresaTelefone() );
        tutor.empresaCnpj( tutorDTO.getEmpresaCnpj() );
        tutor.empresaNome( tutorDTO.getEmpresaNome() );
        tutor.profissao( tutorDTO.getProfissao() );
        tutor.telefoneWhatsapp( tutorDTO.isTelefoneWhatsapp() );
        tutor.vinculo( tutorDTO.getVinculo() );

        return tutor.build();
    }

    @Override
    public TutorDTO toDTO(Tutor modelo) {
        if ( modelo == null ) {
            return null;
        }

        TutorDTO.TutorDTOBuilder tutorDTO = TutorDTO.builder();

        tutorDTO.pessoaNome( modeloPessoaNome( modelo ) );
        tutorDTO.pessoaTelefone( modeloPessoaTelefone( modelo ) );
        tutorDTO.cpf( modelo.getCpf() );
        tutorDTO.empresaTelefone( modelo.getEmpresaTelefone() );
        tutorDTO.empresaCnpj( modelo.getEmpresaCnpj() );
        tutorDTO.empresaNome( modelo.getEmpresaNome() );
        tutorDTO.profissao( modelo.getProfissao() );
        if ( modelo.getTelefoneWhatsapp() != null ) {
            tutorDTO.telefoneWhatsapp( modelo.getTelefoneWhatsapp() );
        }
        tutorDTO.vinculo( modelo.getVinculo() );

        return tutorDTO.build();
    }

    protected Pessoa tutorDTOToPessoa(TutorDTO tutorDTO) {
        if ( tutorDTO == null ) {
            return null;
        }

        Pessoa.PessoaBuilder pessoa = Pessoa.builder();

        pessoa.cpf( tutorDTO.getCpf() );
        pessoa.nome( tutorDTO.getPessoaNome() );
        pessoa.telefone( tutorDTO.getPessoaTelefone() );

        return pessoa.build();
    }

    private String modeloPessoaNome(Tutor tutor) {
        if ( tutor == null ) {
            return null;
        }
        Pessoa pessoa = tutor.getPessoa();
        if ( pessoa == null ) {
            return null;
        }
        String nome = pessoa.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }

    private String modeloPessoaTelefone(Tutor tutor) {
        if ( tutor == null ) {
            return null;
        }
        Pessoa pessoa = tutor.getPessoa();
        if ( pessoa == null ) {
            return null;
        }
        String telefone = pessoa.getTelefone();
        if ( telefone == null ) {
            return null;
        }
        return telefone;
    }
}
