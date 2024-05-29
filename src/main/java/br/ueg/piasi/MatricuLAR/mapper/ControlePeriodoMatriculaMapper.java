package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.ControlePeriodoMatriculaDTO;
import br.ueg.piasi.MatricuLAR.model.ControlePeriodoMatricula;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ControlePeriodoMatriculaMapper extends BaseMapper<ControlePeriodoMatricula, ControlePeriodoMatriculaDTO> {
}
