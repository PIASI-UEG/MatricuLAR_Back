package br.ueg.piasi.MatricuLAR.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DadosTermoDTO {
    private String cpfCrianca;
    private String cpfResponsavel;
    private String nomeResponsavel;
    private String endereco;
}
