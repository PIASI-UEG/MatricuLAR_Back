package br.ueg.piasi.MatricuLAR.dto;

import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaDTO {

    public String cpf;

    public Double renda;

    public StatusMatricula status;

    public LocalDate nascimento;

    public Boolean paisCasados;

    public Boolean moramJuntos;

    public String descricao;

    public Long endereco_id;
}
