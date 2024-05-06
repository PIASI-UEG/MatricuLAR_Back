package br.ueg.piasi.MatricuLAR.dto;

import br.ueg.piasi.MatricuLAR.enums.Turno;
import lombok.Data;

import java.util.List;

@Data
public class TurmaDTO {

    private Long id;
    private String titulo;
    private String nomeProfessor;
    private Turno turno;

    private Integer ano;

////    @Schema(type = "String", pattern = "07:00")
//    @Temporal(TemporalType.TIME)
    private String horaInicio;

//    @Schema(type = "String", pattern = "11:30")
//    @Temporal(TemporalType.TIME)
    private String horaFim;

    private String telefoneProfessor;

    private Long quantidadeAlunos;

    private List<MatriculaListagemDTO> alunos;

}
