package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaListagemDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaVisualizarDTO;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.model.DocumentoMatricula;
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
        TutorMapperImpl.class, DocumentoMatriculaMapperImpl.class, EnderecoMapperImpl.class})
public interface MatriculaMapper extends BaseMapper<Matricula, MatriculaDTO> {

    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "pessoa.nome", target = "nome")
    @Mapping(source = "pessoa.cpf", target = "cpf")
    @Mapping(source = "turma", target = "turma")
    @Mapping(source = "tutorList", target = "tutorDTOList")
    MatriculaDTO toDTO(Matricula modelo);

    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "nome", target = "pessoa.nome")
    @Mapping(source = "cpf", target = "pessoa.cpf")
    @Mapping(source = "turma", target = "turma")
    @Mapping(source = "tutorDTOList", target = "tutorList")
    Matricula toModelo(MatriculaDTO dto);

    @Mapping(target = "nroMatricula", source = "id")
    @Mapping(target = "nomeAluno", source = "pessoa.nome")
    @Mapping(target = "statusMatricula", expression = "java(getStatusMatriculaDescricao(matricula.getStatus()))")
    @Mapping(target = "nomeResponsaveis", expression = "java(getNomeResponsaveisETutores(matricula.getResponsaveis()))")
    @Mapping(target = "telefoneResponsaveis", expression = "java(getTelefoneResponsaveis(matricula.getResponsaveis()))" )
    @Mapping(target = "tituloTurma", source = "turma.titulo", defaultValue = "SEM TURMA")
    MatriculaListagemDTO toMatriculaListagemDTO(Matricula matricula);

    List<MatriculaListagemDTO> toMatriculaListagemDTO(List<Matricula> lista);

    @Mapping(target = "nomeAluno", source = "pessoa.nome")
    @Mapping(target = "cpfAluno", source = "pessoa.cpf")
    @Mapping(target = "nascimento", source = "nascimento")
    @Mapping(target = "statusAluno",  expression = "java(getStatusMatriculaDescricao(matricula.getStatus()))")
    @Mapping(target = "tutoresNomes", expression = "java(getNomeTutores(matricula.getResponsaveis()))")
    @Mapping(target = "tutoresTelefone", expression = "java(getTelefoneTutores(matricula.getResponsaveis()))")
    @Mapping(target = "responsaveisNome", expression = "java(getNomeResponsaveisSemTutores(matricula.getResponsaveis()))")
    @Mapping(target = "caminhoImagem", expression = "java(getCaminhoImagemAluno(matricula.getDocumentoMatricula()))")
    @Mapping(target = "necessidadesEspeciais", source = "necessidades")
    @Mapping(target = "advertencias", source = "advertencias")
    MatriculaVisualizarDTO toMatriculaVisualizarDTO(Matricula matricula);

    default String getStatusMatriculaDescricao(StatusMatricula statusMatricula){
        return statusMatricula.getDescricao();
    }

    default List<String> getNomeResponsaveisETutores(Set<Responsavel> responsaveis){
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
    default List<String> getNomeResponsaveisSemTutores(Set<Responsavel> responsaveis){
        return responsaveis.stream()
                .filter(responsavel -> !responsavel.getTutor())
                .map(Responsavel::getPessoa)
                .map(Pessoa::getNome)
                .toList();
    }
    default List<String> getNomeTutores(Set<Responsavel> responsaveis){
        return responsaveis.stream()
                .filter(Responsavel::getTutor)
                .map(Responsavel::getPessoa)
                .map(Pessoa::getNome)
                .toList();
    }

    default List<String> getTelefoneTutores(Set<Responsavel> responsaveis){
        return responsaveis.stream()
                .filter(Responsavel::getTutor)
                .map(Responsavel::getPessoa)
                .map(Pessoa::getTelefone)
                .filter(Objects::nonNull)
                .toList();
    }

    default String getCaminhoImagemAluno(Set<DocumentoMatricula> documentoMatriculaSet){
        return documentoMatriculaSet.stream().filter(documentoMatricula ->
                documentoMatricula.getIdTipoDocumento().equals("FC"))
                .map(DocumentoMatricula::getCaminhoDocumento).findFirst().orElse(null);
    }

}

