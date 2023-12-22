package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.NecessidadeEspecialDTO;
import br.ueg.piasi.MatricuLAR.model.NecessidadeEspecial;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NecessidadeEspecialMapper extends BaseMapper<NecessidadeEspecial, NecessidadeEspecialDTO> {
}

