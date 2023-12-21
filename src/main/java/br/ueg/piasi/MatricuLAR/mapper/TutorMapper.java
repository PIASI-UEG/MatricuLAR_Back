package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.TutorDTO;
import br.ueg.piasi.MatricuLAR.model.Tutor;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PessoaMapperImpl.class)
public interface TutorMapper extends BaseMapper<Tutor, TutorDTO> {

    @Override
    @Mapping(source = "cpf", target = "pessoa.cpf")
    @Mapping(source = "pessoaNome", target = "pessoa.nome")
    @Mapping(source = "pessoaTelefone", target = "pessoa.telefone")
    @Mapping(source = "cpf", target = "cpf")
    Tutor toModelo(TutorDTO tutorDTO);

    @Override
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    @Mapping(source = "pessoa.telefone", target = "pessoaTelefone")
    @Mapping(source = "cpf", target = "cpf")
    TutorDTO toDTO(Tutor modelo);
}

