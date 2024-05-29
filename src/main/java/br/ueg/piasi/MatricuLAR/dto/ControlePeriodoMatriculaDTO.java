package br.ueg.piasi.MatricuLAR.dto;

import java.time.LocalDate;

public record ControlePeriodoMatriculaDTO(Boolean aceitandoCadastroMatricula,
                                          LocalDate dataInicio,
                                          LocalDate dataFim,
                                          Boolean atualizarPeriodoAutomatico) {
}
