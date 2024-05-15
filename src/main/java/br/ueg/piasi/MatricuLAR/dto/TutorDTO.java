package br.ueg.piasi.MatricuLAR.dto;


import br.ueg.piasi.MatricuLAR.enums.Vinculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutorDTO {

    private String cpf;
    private String empresaTelefone;
    private String empresaCnpj;
    private String empresaNome;
    private String profissao;
    private boolean telefoneWhatsapp;
    private String nomeTutor;
    private String pessoaTelefone;
    private LocalDate dataNascimento;
    private Vinculo vinculo;
    private Boolean paisCasados;
    private Boolean moraComConjuge;
}
