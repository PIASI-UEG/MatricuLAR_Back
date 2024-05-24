package br.ueg.piasi.MatricuLAR.dto;

import br.ueg.piasi.MatricuLAR.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;

    private String senha;

    private Cargo cargo;

    private String email;

    private String pessoaCpf;

    private String pessoaNome;

    private String pessoaTelefone;

    private Long idUsuarioRequisicao;

}
