package br.ueg.piasi.MatricuLAR.dto;

import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;

import java.util.List;

public record MatriculaListagemDTO(Long nroMatricula,
                                   String nomeAluno,
                                   String tituloTurma,
                                   List<String> nomeResponsaveis,
                                   List<String> telefoneResponsaveis,
                                   List<String> nomeTutores,
                                   String statusMatricula) {
}
