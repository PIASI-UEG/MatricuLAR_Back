package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.*;
import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.piasi.MatricuLAR.model.*;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ResponsavelMapperImpl.class, NecessidadeEspecialMapperImpl.class,
        TutorMapperImpl.class, DocumentoMatriculaMapperImpl.class, EnderecoMapperImpl.class,
        InformacoesMatriculaMapperImpl.class, AdvertenciaMapperImpl.class})
public interface MatriculaMapper extends BaseMapper<Matricula, MatriculaDTO> {

    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "pessoa.nome", target = "nome")
    @Mapping(source = "pessoa.cpf", target = "cpf")
    @Mapping(source = "turma", target = "turma")
    @Mapping(source = "tutorList", target = "tutorDTOList", qualifiedByName = "tutorListInvertida")
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
    @Mapping(target = "tituloTurma", source = "turma.titulo", defaultValue = "Sem turma")
    MatriculaListagemDTO toMatriculaListagemDTO(Matricula matricula);

    List<MatriculaListagemDTO> toMatriculaListagemDTO(List<Matricula> lista);

    @Mapping(target = "nomeAluno", source = "pessoa.nome")
    @Mapping(target = "cpfAluno", source = "pessoa.cpf")
    @Mapping(target = "nascimento", source = "nascimento")
    @Mapping(target = "statusAluno",  expression = "java(getStatusMatriculaDescricao(matricula.getStatus()))")
    @Mapping(target = "tutoresNomes", expression = "java(getNomeTutores(matricula.getResponsaveis()))")
    @Mapping(target = "tutoresTelefone", expression = "java(getTelefoneTutores(matricula.getResponsaveis()))")
    @Mapping(target = "responsaveis", expression = "java(getResponsaveisSemTutores(matricula.getResponsaveis()))")
    @Mapping(target = "responsaveisNome", expression = "java(getNomeResponsaveisETutores(matricula.getResponsaveis()))")
    @Mapping(target = "caminhoImagem", expression = "java(getCaminhoImagemAluno(matricula.getDocumentoMatricula()))")
    @Mapping(target = "necessidades", source = "necessidades")
    @Mapping(target = "advertencias", source = "advertencias")
    MatriculaVisualizarDTO toMatriculaVisualizarDTO(Matricula matricula);

    @Mapping(target = "nomeAluno", source = "pessoa.nome")
    @Mapping(target = "cpfAluno", source = "pessoa.cpf")
    @Mapping(target = "nascimento", source = "nascimento")
    @Mapping(target = "statusAluno",  expression = "java(getStatusMatriculaDescricao(matricula.getStatus()))")
    @Mapping(target = "tutores", source = "tutorList")
    @Mapping(target = "caminhoImagem", expression = "java(getCaminhoImagemAluno(matricula.getDocumentoMatricula()))")
    @Mapping(target = "necessidadesEspeciais", source = "necessidades")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "endereco", expression = "java(getEndereco(matricula.getEndereco()))")
    @Mapping(target = "turma", source = "turma.titulo", defaultValue = "Sem turma")
    MatriculaRelatorioDTO toMatriculaRelatorioDTO(Matricula matricula);

    default String getStatusMatriculaDescricao(StatusMatricula statusMatricula){
        return statusMatricula.getDescricao();
    }

    default String getEndereco(Endereco endereco){
        return endereco.getCidade()+", "
                +endereco.getBairro()+", "
                +endereco.getLogradouro()+", "
                +endereco.getComplemento();
    }

    default List<String> getNomeResponsaveisETutores(Set<Responsavel> responsaveis){
        return new ArrayList<>(responsaveis.stream()
                .map(Responsavel::getPessoa)
                .map(Pessoa::getNome)
                .toList());
    }

    default List<String> getTelefoneResponsaveis(Set<Responsavel> responsaveis){
        return responsaveis.stream()
                .map(Responsavel::getPessoa)
                .map(Pessoa::getTelefone)
                .filter(Objects::nonNull)
                .toList();
    }
    default List<ResponsavelDTO> getResponsaveisSemTutores(Set<Responsavel> responsaveis){
        List<Responsavel> lista = new ArrayList<>(responsaveis.stream()
                .filter(responsavel -> !responsavel.getTutor())
                .toList());
        Collections.reverse(lista);
        ResponsavelMapperImpl responseMapper = new ResponsavelMapperImpl();
        return responseMapper.toDTO(lista);
    }
    default List<String> getNomeTutores(Set<Responsavel> responsaveis){
        List<String> lista = new ArrayList<>(responsaveis.stream()
                .filter(Responsavel::getTutor)
                .map(Responsavel::getPessoa)
                .map(Pessoa::getNome)
                .sorted(Collections.reverseOrder())
                .toList());
        Collections.reverse(lista);
        return lista;
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

    @Named("tutorListInvertida")
     default List<TutorDTO> getTutorListEInverteOrdem(List<Tutor> tutores){
        TutorMapperImpl tutorMapper = new TutorMapperImpl();
        List<TutorDTO> tutorDTOS = tutorMapper.toDTO(tutores);
        if (Objects.nonNull(tutorDTOS)){
            Collections.reverse(tutorDTOS);
        }
        return tutorDTOS;
    }

}

