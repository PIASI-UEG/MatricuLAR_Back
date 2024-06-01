package br.ueg.piasi.MatricuLAR.dto;

import br.ueg.piasi.MatricuLAR.model.Tutor;
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
public class MatriculaRelatorioDTO {
    private String nomeAluno;
    private String cpfAluno;
    private LocalDate nascimento;
    private String statusAluno;
    private List<TutorDTO> tutores;
    private List<ResponsavelDTO> responsaveis;
    private String caminhoImagem;
    private List<NecessidadeEspecialDTO> necessidadesEspeciais;
    private List<AdvertenciaDTO> advertencias;
}
