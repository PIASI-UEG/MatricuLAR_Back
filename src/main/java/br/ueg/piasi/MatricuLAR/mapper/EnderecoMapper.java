package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.EnderecoDTO;
import br.ueg.piasi.MatricuLAR.dto.PessoaDTO;
import br.ueg.piasi.MatricuLAR.model.Endereco;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.prog.webi.api.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper extends BaseMapper<Endereco, EnderecoDTO> {
}

