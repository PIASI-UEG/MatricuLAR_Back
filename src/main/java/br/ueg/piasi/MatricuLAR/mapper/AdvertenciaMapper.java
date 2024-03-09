package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.AdvertenciaDTO;
import br.ueg.piasi.MatricuLAR.model.Advertencia;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdvertenciaMapper extends BaseMapper<Advertencia, AdvertenciaDTO> {
    @Override
    @Mapping(source = "idMatricula", target = "matricula.id")
    Advertencia toModelo(AdvertenciaDTO DTO);

    @Override
    @Mapping(source = "matricula.id", target = "idMatricula")
    AdvertenciaDTO toDTO(Advertencia modelo);
}