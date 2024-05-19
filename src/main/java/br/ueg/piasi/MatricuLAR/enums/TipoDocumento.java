package br.ueg.piasi.MatricuLAR.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TipoDocumento {

    FOTO_CRIANCA("FC", "Foto da criança", 0),
    CERTIDAO_NASCIMENTO("CNC", "Certidão de nascimento da criança", 1),
    CPF_CRIANCA("CPFC", "CPF da criança", 2),
    DOCUMENTO_VEICULO("DV", "Documento do veículo", 3),
    COMPROVANTE_ENDERECO("CE", "Comprovante de endereço", 4),
    COMPROVANTE_MORADIA("CM", "Comprovante de estado da moradia", 5),
    COMPROVANTE_BOLSA_FAMILIA("CBF", "Comprovante de beneficiário do programa Bolsa Família", 6),
    ENCAMINHAMENTO_CRAS("CRAS", "Declaração de encaminhamento do CRAS", 7),
    CPF_TUTOR1("CPFT1", "CPF do tutor 1", 8),
    CPF_TUTOR2("CPFT2", "CPF do tutor 2", 9),
    CERTIDAO_ESTADO_CIVIL("CEC", "Certidão de estado civil dos tutores", 10),
    COMPROVANTE_TRABALHO_T1("CTR1", "Carteira de trablho / Comprovante de trabalho do tutor 1", 11),
    CONTRA_CHEQUE1T1("CC1T1","Contra-cheque 1 do tutor1", 12),
    CONTRA_CHEQUE2T1("CC2T1","Contra-cheque 2 do tutor1", 13),
    CONTRA_CHEQUE3T1("CC3T1","Contra-cheque 3 do tutor1", 14),
    CONTRA_CHEQUE1T2("CC1T2","Contra-cheque 1 do tutor2", 15),
    CONTRA_CHEQUE2T2("CC2T2","Contra-cheque 2 do tutor2", 16),
    CONTRA_CHEQUE3T2("CC3T2","Contra-cheque 3 do tutor2", 17),
    COMPROVANTE_TRABALHO_T2("CTR2", "Carteira de trablho / Comprovante de trabalho do tutor 2", 18),
    DECLARACAO_ESCOLART1("DCT1", "Declaração escolar do tutor 1", 19),
    DECLARACAO_ESCOLART2("DCT2", "Declaração escolar do tutor 2", 20)
    ;

    private final String id;
    private final String descricao;
    private final int posicaoArray;

    TipoDocumento(String id, String descricao, int posicaoArray){
        this.id = id;
        this.descricao = descricao;
        this.posicaoArray = posicaoArray;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
    public int getPosicaoArray() {return posicaoArray;}

    public static TipoDocumento getByPosicaoArray(int posicaoArray){
        return Arrays.stream(values())
                .filter(val -> val.getPosicaoArray() == posicaoArray)
                .findFirst().orElse(null);
    }
    public static TipoDocumento getById(final String id) {
        return Arrays.stream(values()).filter
                (value -> value.getId().equals(id)).findFirst().orElse(null);
    }
    public static List<TipoDocumento> getDocumentosNaoObrigatoriosCasados(){
         List<TipoDocumento> documentos = new ArrayList<>();
         documentos.add(TipoDocumento.DOCUMENTO_VEICULO);
         documentos.add(TipoDocumento.COMPROVANTE_BOLSA_FAMILIA);
         documentos.add(TipoDocumento.ENCAMINHAMENTO_CRAS);
         return documentos;
    }
    public static List<TipoDocumento> getDocumentosNaoObrigatoriosGerais(){
        List<TipoDocumento> documentos = getDocumentosNaoObrigatoriosCasados();
        documentos.add(TipoDocumento.CONTRA_CHEQUE1T2);
        documentos.add(TipoDocumento.CONTRA_CHEQUE2T2);
        documentos.add(TipoDocumento.CONTRA_CHEQUE3T2);
        documentos.add(TipoDocumento.COMPROVANTE_TRABALHO_T2);
        documentos.add(TipoDocumento.DECLARACAO_ESCOLART2);
        documentos.add(TipoDocumento.CPF_TUTOR2);
        return documentos;
    }
}
