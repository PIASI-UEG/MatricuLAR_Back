package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.AdvertenciaDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaNecessidadeDTO;
import br.ueg.piasi.MatricuLAR.model.Advertencia;
import br.ueg.piasi.MatricuLAR.model.MatriculaNecessidade;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatriculaNecessidadeMapper extends BaseMapper<MatriculaNecessidade, MatriculaNecessidadeDTO> {

    @Override
    @Mapping(source = "matriculaCpf", target = "matricula.cpf")
    @Mapping(source = "necessidadeEspecialId", target = "necessidadeEspecial.id")
    MatriculaNecessidade toModelo(MatriculaNecessidadeDTO matriculaNecessidadeDTO);

    @Override
    @Mapping(source = "matricula.cpf", target = "matriculaCpf")
    @Mapping(source = "necessidadeEspecial.id", target = "necessidadeEspecialId")
    MatriculaNecessidadeDTO toDTO(MatriculaNecessidade modelo);
}