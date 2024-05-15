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

    private Boolean frequentouOutraCreche;

    private String razaoSaida;

    private String tipoResidencia;

    private Long valorAluguel;

    private Boolean possuiBeneficiosDoGoverno;

    private Long valorBeneficio;

    private BigDecimal rendaFamiliar;

    private String observacao;
}
