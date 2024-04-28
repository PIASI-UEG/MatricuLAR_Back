package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.UsuarioDTO;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Usuario;
import br.ueg.prog.webi.api.model.IEntidade;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-28T16:05:57-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Amazon.com Inc.)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public List<UsuarioDTO> toDTO(List<Usuario> lista) {
        if ( lista == null ) {
            return null;
        }

        List<UsuarioDTO> list = new ArrayList<UsuarioDTO>( lista.size() );
        for ( Usuario usuario : lista ) {
            list.add( toDTO( usuario ) );
        }

        return list;
    }

    @Override
    public void updateModel(Usuario source, Usuario target) {
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
        if ( source.getSenha() != null ) {
            target.setSenha( source.getSenha() );
        }
        if ( source.getCargo() != null ) {
            target.setCargo( source.getCargo() );
        }
        if ( source.getEmail() != null ) {
            target.setEmail( source.getEmail() );
        }
        if ( source.getPessoa() != null ) {
            target.setPessoa( source.getPessoa() );
        }
    }

    @Override
    public Usuario toModelo(UsuarioDTO usuarioDTO) {
        if ( usuarioDTO == null ) {
            return null;
        }

        Usuario.UsuarioBuilder usuario = Usuario.builder();

        usuario.pessoa( usuarioDTOToPessoa( usuarioDTO ) );
        usuario.id( usuarioDTO.getId() );
        usuario.senha( usuarioDTO.getSenha() );
        usuario.cargo( usuarioDTO.getCargo() );
        usuario.email( usuarioDTO.getEmail() );

        return usuario.build();
    }

    @Override
    public UsuarioDTO toDTO(Usuario modelo) {
        if ( modelo == null ) {
            return null;
        }

        UsuarioDTO.UsuarioDTOBuilder usuarioDTO = UsuarioDTO.builder();

        usuarioDTO.pessoaCpf( modeloPessoaCpf( modelo ) );
        usuarioDTO.pessoaNome( modeloPessoaNome( modelo ) );
        usuarioDTO.pessoaTelefone( modeloPessoaTelefone( modelo ) );
        usuarioDTO.id( modelo.getId() );
        usuarioDTO.cargo( modelo.getCargo() );
        usuarioDTO.email( modelo.getEmail() );

        return usuarioDTO.build();
    }

    protected Pessoa usuarioDTOToPessoa(UsuarioDTO usuarioDTO) {
        if ( usuarioDTO == null ) {
            return null;
        }

        Pessoa.PessoaBuilder pessoa = Pessoa.builder();

        pessoa.cpf( usuarioDTO.getPessoaCpf() );
        pessoa.nome( usuarioDTO.getPessoaNome() );
        pessoa.telefone( usuarioDTO.getPessoaTelefone() );

        return pessoa.build();
    }

    private String modeloPessoaCpf(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }
        Pessoa pessoa = usuario.getPessoa();
        if ( pessoa == null ) {
            return null;
        }
        String cpf = pessoa.getCpf();
        if ( cpf == null ) {
            return null;
        }
        return cpf;
    }

    private String modeloPessoaNome(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }
        Pessoa pessoa = usuario.getPessoa();
        if ( pessoa == null ) {
            return null;
        }
        String nome = pessoa.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }

    private String modeloPessoaTelefone(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }
        Pessoa pessoa = usuario.getPessoa();
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
