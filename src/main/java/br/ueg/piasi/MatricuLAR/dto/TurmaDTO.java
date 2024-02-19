package br.ueg.piasi.MatricuLAR.dto;

import br.ueg.piasi.MatricuLAR.enums.Turno;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class TurmaDTO {

    private Long id;
    private String titulo;
    private String nomeProfessor;
    private Turno turno;

    private Integer ano;

    @Schema(type = "String", pattern = "07:00")
    @Temporal(TemporalType.TIME)
    private LocalTime horaInicio;

    @Schema(type = "String", pattern = "11:30")
    @Temporal(TemporalType.TIME)
    private LocalTime horaFim;

    private List<MatriculaTurmaDTO> turmaMatriculas;
}
