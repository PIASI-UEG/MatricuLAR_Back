package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.AdvertenciaDTO;
import br.ueg.piasi.MatricuLAR.dto.PessoaDTO;
import br.ueg.piasi.MatricuLAR.dto.UsuarioDTO;
import br.ueg.piasi.MatricuLAR.model.Advertencia;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Usuario;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdvertenciaMapper extends BaseMapper<Advertencia, AdvertenciaDTO> {
    @Override
    @Mapping(source = "cpf_matricula", target = "matricula.cpf")
    Advertencia toModelo(AdvertenciaDTO DTO);

    @Override
    @Mapping(source = "matricula.cpf", target = "cpf_matricula")
    AdvertenciaDTO toDTO(Advertencia modelo);
}