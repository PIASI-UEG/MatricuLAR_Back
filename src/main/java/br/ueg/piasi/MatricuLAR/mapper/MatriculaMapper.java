package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatriculaMapper extends BaseMapper<Matricula, MatriculaDTO> {

    @Mapping(source = "endereco.id", target = "endereco_id")
    @Mapping(source = "pessoa.nome", target = "nome")
    @Mapping(source = "pessoa.cpf", target = "cpf")
    MatriculaDTO toDTO(Matricula modelo);

    @Mapping(source = "endereco_id", target = "endereco.id")
    @Mapping(source = "nome", target = "pessoa.nome")
    @Mapping(source = "cpf", target = "pessoa.cpf")
    Matricula toModelo(MatriculaDTO dto);
}

