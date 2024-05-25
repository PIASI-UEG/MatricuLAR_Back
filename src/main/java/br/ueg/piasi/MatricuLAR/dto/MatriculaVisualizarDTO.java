package br.ueg.piasi.MatricuLAR.dto;

import java.time.LocalDate;
import java.util.List;

public record MatriculaVisualizarDTO(
        String nomeAluno,
        String cpfAluno,
        LocalDate nascimento,
        String statusAluno,
        List<String> tutoresNomes,
        List<String> tutoresTelefone,
        List<String> responsaveisNome,
        String caminhoImagem,
        List<NecessidadeEspecialDTO> necessidadesEspeciais,
        List<AdvertenciaDTO> advertencias
)
{ }
