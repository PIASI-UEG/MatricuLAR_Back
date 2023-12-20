package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.UsuarioDTO;
import br.ueg.piasi.MatricuLAR.model.Usuario;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO> {

    @Override
    @Mapping(source = "pessoaCpf", target = "pessoa.cpf")
    @Mapping(source = "pessoaNome", target = "pessoa.nome")
    @Mapping(source = "pessoaFone", target = "pessoa.fone")
    Usuario toModelo(UsuarioDTO usuarioDTO);

    @Override
    @Mapping(source = "pessoa.cpf", target = "pessoaCpf")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    @Mapping(source = "pessoa.fone", target = "pessoaFone")
    UsuarioDTO toDTO(Usuario modelo);
}
