package br.ueg.piasi.MatricuLAR.exception;

import br.ueg.prog.webi.api.exception.MessageCode;

public enum SistemaMessageCode implements MessageCode {

    ERRO_EXCLUIR_ADMIN("MSG-001", 400),
    ERRO_USUARIO_NAO_EXISTE("MSG-002", 400),
    ERRO_SOMENTE_DONO_ALTERA_SENHA("MSG-003", 400),

    ERRO_INCLUIR_DOCUMENTO("MSG-101", 503),
    ERRO_INCLUIR_DOCUMENTO_MATRICULA_NAO_ENCONTRADA("MSG-102", 400),
    ERRO_ENCONTRAR_DOCUMENTO_ARQUIVO_NAO_ENCONTRADO("MSG-103", 503)
    ;

    private final String code;
    private final Integer status;
    SistemaMessageCode(final String code, final Integer status) {
        this.code = code;
        this.status = status;
    }
    public String getCode() { return code; }
    public Integer getStatus() { return status; }
    @Override
    public String toString() { return code; }
}
