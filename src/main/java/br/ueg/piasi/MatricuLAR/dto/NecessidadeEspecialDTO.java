package br.ueg.piasi.MatricuLAR.dto;


import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class NecessidadeEspecialDTO {

    private Long id;
    private Long matriculaId;
    private String titulo;

}
