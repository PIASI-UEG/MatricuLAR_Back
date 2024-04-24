package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaListagemDTO;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Responsavel;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {ResponsavelMapperImpl.class, NecessidadeEspecialMapperImpl.class,
        TutorMapperImpl.class, DocumentoMatriculaMapperImpl.class})
public interface MatriculaMapper extends BaseMapper<Matricula, MatriculaDTO> {

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "pessoa.nome", target = "nome")
    @Mapping(source = "pessoa.cpf", target = "cpf")
    @Mapping(source = "turma", target = "turma")
    @Mapping(source = "tutorList", target = "tutorDTOList")
    MatriculaDTO toDTO(Matricula modelo);

    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "nome", target = "pessoa.nome")
    @Mapping(source = "cpf", target = "pessoa.cpf")
    @Mapping(source = "turma", target = "turma")
    @Mapping(source = "tutorDTOList", target = "tutorList")
    Matricula toModelo(MatriculaDTO dto);

    @Mapping(target = "nroMatricula", source = "id")
    @Mapping(target = "nomeAluno", source = "pessoa.nome")
    @Mapping(target = "statusMatricula", expression = "java(getStatusMatriculaDescricao(matricula.getStatus()))")
    @Mapping(target = "nomeResponsaveis", expression = "java(getNomeResponsaveis(matricula.getResponsaveis()))")
    @Mapping(target = "telefoneResponsaveis", expression = "java(getTelefoneResponsaveis(matricula.getResponsaveis()))" )
    @Mapping(target = "tituloTurma", source = "turma.titulo", defaultValue = "SEM TURMA")
    MatriculaListagemDTO toMatriculaListagemDTO(Matricula matricula);

    List<MatriculaListagemDTO> toMatriculaListagemDTO(List<Matricula> lista);


    default String getStatusMatriculaDescricao(StatusMatricula statusMatricula){
        return statusMatricula.getDescricao();
    }

    default List<String> getNomeResponsaveis(Set<Responsavel> responsaveis){
        return responsaveis.stream()
                .map(Responsavel::getPessoa)
                .map(Pessoa::getNome)
                .toList();
    }

    default List<String> getTelefoneResponsaveis(Set<Responsavel> responsaveis){
        return responsaveis.stream()
                .map(Responsavel::getPessoa)
                .map(Pessoa::getTelefone)
                .filter(Objects::nonNull)
                .toList();
    }
}

