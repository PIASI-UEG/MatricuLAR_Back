package br.ueg.piasi.MatricuLAR.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Builder
@Data
public class NecessidadeEspecialDTO {

    private Long id;
    private String titulo;

}
