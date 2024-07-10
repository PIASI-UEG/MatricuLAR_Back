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

    private String horaInicio;

    private String horaFim;

    private String telefoneProfessor;

    private Long quantidadeAlunos;

    private List<MatriculaListagemDTO> alunos;

}
