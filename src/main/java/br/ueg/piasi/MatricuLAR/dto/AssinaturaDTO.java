package br.ueg.piasi.MatricuLAR.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssinaturaDTO {
    private String imagemAss;
    private String cpfResponsavel;
    private String cpfCrianca;
    private String nomeResponsavel;
    private String endereco;
}
