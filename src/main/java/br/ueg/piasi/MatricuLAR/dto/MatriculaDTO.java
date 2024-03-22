package br.ueg.piasi.MatricuLAR.dto;

import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaDTO {

    private Long id;

    private String cpf;

    private String nome;

    private StatusMatricula status;

    @Temporal(TemporalType.DATE)
    private LocalDate nascimento;

    private Long enderecoId;

    private List<TutorDTO> tutorDTOList;

    private List<NecessidadeEspecialDTO> necessidades;

    private List<ResponsavelDTO> responsaveis;

    private List<AdvertenciaDTO> advertencias;

    private TurmaDTO turma;

    private InformacoesMatriculaDTO informacoesMatricula;

    private List<DocumentoMatriculaDTO> documentoMatricula;

}
