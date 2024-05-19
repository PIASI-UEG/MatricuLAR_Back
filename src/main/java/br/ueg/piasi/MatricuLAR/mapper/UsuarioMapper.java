package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.UsuarioAlterarDTO;
import br.ueg.piasi.MatricuLAR.dto.UsuarioDTO;
import br.ueg.piasi.MatricuLAR.model.Usuario;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {PessoaMapperImpl.class})
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO> {

    @Override
    @Mapping(source = "pessoaCpf", target = "pessoa.cpf")
    @Mapping(source = "pessoaNome", target = "pessoa.nome")
    @Mapping(source = "pessoaTelefone", target = "pessoa.telefone")
    Usuario toModelo(UsuarioDTO usuarioDTO);

    @Override
    @Mapping(source = "pessoa.cpf", target = "pessoaCpf")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    @Mapping(source = "pessoa.telefone", target = "pessoaTelefone")
    @Mapping(source = "senha", target = "senha", ignore = true)
    UsuarioDTO toDTO(Usuario modelo);

    @Mapping(source = "pessoaCpf", target = "pessoa.cpf")
    @Mapping(source = "pessoaNome", target = "pessoa.nome")
    @Mapping(source = "pessoaTelefone", target = "pessoa.telefone")
    Usuario toUsuario(UsuarioAlterarDTO usuarioAlterarDTO);
}
