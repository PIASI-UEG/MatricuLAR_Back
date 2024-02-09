package br.ueg.piasi.MatricuLAR.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NecessidadeEspecialDTO {

    private Long id;
    private String titulo;
    private String observacoes;

    @JsonIgnore
    private List<MatriculaNecessidadeDTO>  matriculaNecessidade = new ArrayList<>();

}
