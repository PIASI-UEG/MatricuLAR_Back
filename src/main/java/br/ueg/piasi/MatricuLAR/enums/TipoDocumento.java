package br.ueg.piasi.MatricuLAR.enums;

import java.util.Arrays;

public enum TipoDocumento {

    FOTO_CRIANCA("FC", "Foto da criança"),
    CERTIDAO_NASCIMENTO("CNC", "Certidão de nascimento da criança"),
    CPF_CRIANCA("CPFC", "CPF da criança"),
    DOCUMENTO_VEICULO("DV", "Documento do veículo"),
    COMPROVANTE_ENDERECO("CE", "Comprovante de endereço"),
    COMPROVANTE_MORADIA("CM", "Comprovante de estado da moradia"),
    COMPROVANTE_BOLSA_FAMILIA("CBF", "Comprovante de beneficiário do programa Bolsa Família"),
    ENCAMINHAMENTO_CRAS("CRAS", "Declaração de encaminhamento do CRAS"),
    CPF_TUTOR1("CPFT1", "CPF do tutor 1"),
    CPF_TUTOR2("CPFT2", "CPF do tutor 2"),
    CERTIDAO_ESTADO_CIVIL("CEC", "Certidão de estado civil dos tutores"),
    COMPROVANTE_TRABALHO_T1("CTR1", "Carteira de trablho / Comprovante de trabalho do tutor 1"),
    CONTRA_CHEQUE1T1("CC1T1","Contra-cheque 1 do tutor1"),
    CONTRA_CHEQUE2T1("CC2T1","Contra-cheque 2 do tutor1"),
    CONTRA_CHEQUE3T1("CC3T1","Contra-cheque 3 do tutor1"),
    CONTRA_CHEQUE1T2("CC1T2","Contra-cheque 1 do tutor2"),
    CONTRA_CHEQUE2T2("CC2T2","Contra-cheque 2 do tutor2"),
    CONTRA_CHEQUE3T2("CC3T2","Contra-cheque 3 do tutor2"),
    COMPROVANTE_TRABALHO_T2("CTR2", "Carteira de trablho / Comprovante de trabalho do tutor 2"),
    DECLARACAO_ESCOLART1("DCT1", "Declaração escolar do tutor 1"),
    DECLARACAO_ESCOLART2("DCT2", "Declaração escolar do tutor 2"),
    CERTIDAO_ESTADO_CIVIL2("CEC2", "Certidão de estado civil para tutores solteiros")
    ;

    private final String id;
    private final String descricao;

    TipoDocumento(String id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoDocumento getById(final String id) {
        return Arrays.stream(values()).filter
                (value -> value.getId().equals(id)).findFirst().orElse(null);
    }
}
