package br.ueg.piasi.MatricuLAR.dto;

import br.ueg.piasi.MatricuLAR.enums.Vinculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsavelDTO {

    private String cpfResponsavel;
    private String nomeResponsavel;
    private Long idMatricula;
    private String nomeMatricula;
    private Vinculo vinculo;
    private Boolean tutor;

}
