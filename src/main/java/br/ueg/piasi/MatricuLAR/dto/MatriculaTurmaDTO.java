package br.ueg.piasi.MatricuLAR.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaTurmaDTO {

    private Long turmaId;

    private String matriculaCpf;

    @Temporal(TemporalType.DATE)
    private LocalDate entradaTurma;
}
