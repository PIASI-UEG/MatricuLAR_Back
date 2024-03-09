package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ResponsavelMapperImpl.class, NecessidadeEspecialMapperImpl.class, TutorMapperImpl.class})
public interface MatriculaMapper extends BaseMapper<Matricula, MatriculaDTO> {

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "pessoa.nome", target = "nome")
    @Mapping(source = "pessoa.cpf", target = "cpf")
    @Mapping(source = "turma", target = "turma")
    MatriculaDTO toDTO(Matricula modelo);

    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "nome", target = "pessoa.nome")
    @Mapping(source = "cpf", target = "pessoa.cpf")
    @Mapping(source = "turma", target = "turma")
    @Mapping(source = "tutorDTOList", target = "tutorList")
    Matricula toModelo(MatriculaDTO dto);
}

