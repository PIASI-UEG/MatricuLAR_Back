package br.ueg.piasi.MatricuLAR.dto;

import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaDTO {

    private String cpf;

    private String nome;

    private Double renda;

    private StatusMatricula status;

    @Temporal(TemporalType.DATE)
    private LocalDate nascimento;

    private Boolean paisCasados;

    private Boolean moramJuntos;

    private String descricao;

    private Long endereco_id;

    private List<MatriculaNecessidadeDTO> matriculaNecessidades = new ArrayList<>();

}
