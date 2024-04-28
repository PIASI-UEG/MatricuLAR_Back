package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.MatriculaListagemDTO;
import br.ueg.piasi.MatricuLAR.dto.TurmaDTO;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.model.Turma;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring" )
public interface TurmaMapper extends BaseMapper<Turma, TurmaDTO> {

    @Mapping(target = "alunos", expression = "java(toMatriculaListagemDTO(turma.getAlunos()))")
    @Mapping(target = "quantidadeAlunos", expression = "java(getQuantidadeAlunos(turma.getAlunos()))")
    TurmaDTO toDTO(Turma turma);

    default List<MatriculaListagemDTO> toMatriculaListagemDTO(Set<Matricula> matriculas){
        MatriculaMapperImpl matriculaMapperImpl = new MatriculaMapperImpl();
        return matriculaMapperImpl.toMatriculaListagemDTO(matriculas.stream().toList());
    }

    default Long getQuantidadeAlunos(Set<Matricula> matriculas){
        return (long) matriculas.size();
    }
}

