package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.InformacoesMatriculaDTO;
import br.ueg.piasi.MatricuLAR.model.InformacoesMatricula;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InformacoesMatriculaMapper extends BaseMapper<InformacoesMatricula, InformacoesMatriculaDTO> {

    @Override
    @Mapping(source = "id", target = "matricula.id")
    InformacoesMatricula toModelo(InformacoesMatriculaDTO DTO);

    @Override
    @Mapping(source = "matricula.id", target = "id")
    InformacoesMatriculaDTO toDTO(InformacoesMatricula modelo);
}