package br.ueg.piasi.MatricuLAR.dto;


import br.ueg.piasi.MatricuLAR.enums.Vinculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String pessoaNome;
    private String pessoaTelefone;

    private Vinculo vinculo;

}
