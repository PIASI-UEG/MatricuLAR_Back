package br.ueg.piasi.MatricuLAR.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssinaturaDTO {
    private byte[] imagemAss;
    private String CPFAss;
}
