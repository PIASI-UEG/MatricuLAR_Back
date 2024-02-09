package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.MatriculaTurmaDTO;
import br.ueg.piasi.MatricuLAR.model.MatriculaTurma;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatriculaTurmaMapper extends BaseMapper<MatriculaTurma, MatriculaTurmaDTO> {

    @Override
    @Mapping(source = "turmaId", target = "turma.id")
    @Mapping(source = "matriculaCpf", target = "matricula.cpf")
    MatriculaTurma toModelo(MatriculaTurmaDTO matriculaTurmaDTO);

    @Override
    @Mapping(source = "turma.id", target = "turmaId")
    @Mapping(source = "matricula.cpf", target = "matriculaCpf")
    MatriculaTurmaDTO toDTO(MatriculaTurma modelo);
}