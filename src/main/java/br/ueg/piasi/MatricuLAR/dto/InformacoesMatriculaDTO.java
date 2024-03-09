package br.ueg.piasi.MatricuLAR.dto;

import br.ueg.piasi.MatricuLAR.enums.TipoResidencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InformacoesMatriculaDTO {

    private Long id;

    private Boolean esteveOutraCreche;

    private String razaoSaidaCreche;

    private TipoResidencia tipoResidencia;

    private Long valorAluguel;

    private Boolean beneficiarioGoverno;

    private Long valorBeneficio;

    private BigDecimal rendaFamiliar;

    private Boolean paisCasados;

    private Boolean moramJuntos;

    private String observacao;
}
