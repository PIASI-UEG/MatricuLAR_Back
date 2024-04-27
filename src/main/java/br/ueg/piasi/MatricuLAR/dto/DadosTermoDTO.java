package br.ueg.piasi.MatricuLAR.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DadosTermoDTO {
    private String cpfCrianca;
    private String cpfResponsavel;
    private String nomeResponsavel;
    private String endereco;
}
