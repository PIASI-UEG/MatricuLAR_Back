package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.ResponsavelDTO;
import br.ueg.piasi.MatricuLAR.model.Responsavel;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResponsavelMapper extends BaseMapper<Responsavel, ResponsavelDTO> {


    @Mapping(source = "pessoa.nome", target = "nomeResponsavel")
    @Mapping(source = "pessoa.cpf", target = "cpfResponsavel")
    @Mapping(source = "matricula.cpf", target = "cpfAluno")
    @Mapping(source = "matricula.pessoa.nome", target = "nomeAluno")
    ResponsavelDTO toDTO(Responsavel modelo);

    @Mapping(source = "cpfResponsavel", target = "pessoa.cpf")
    @Mapping(source = "cpfAluno", target = "matricula.cpf")
    Responsavel toModelo(ResponsavelDTO dto);
}

