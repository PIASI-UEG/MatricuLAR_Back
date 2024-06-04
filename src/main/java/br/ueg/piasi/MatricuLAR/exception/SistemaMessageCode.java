package br.ueg.piasi.MatricuLAR.exception;

import br.ueg.prog.webi.api.exception.MessageCode;

public enum SistemaMessageCode implements MessageCode {

    /*
        001 - 050 mensagens relacionadas a usuario
        051 - 100 mensagens para tutor
        101 - 150 mensagens para documento
        151 - 200 mensagens para turma
        201 - 300 mensagens para matricula
        301 - 400 mensagens para termo
        401 - 450 mensagens para periodo matricula
    */

    //USUARIO
    ERRO_EXCLUIR_ADMIN("MSG-001", 400),
    ERRO_USUARIO_NAO_EXISTE("MSG-002", 400),
    ERRO_SOMENTE_DONO_ALTERA_SENHA("MSG-003", 400),
    ERRO_EMAIL_INCORRETO("MSG-004", 400),
    SENHA_ANTIGA_INCORRETA("MSG-005", 400),
    ERRO_JA_EXISTE_USUARIO_ADMIN("MSG-006", 400),

    //DOCUMENTOS
    ERRO_INCLUIR_DOCUMENTO("MSG-101", 500),
    ERRO_INCLUIR_DOCUMENTO_MATRICULA_NAO_ENCONTRADA("MSG-102", 400),
    ERRO_ENCONTRAR_DOCUMENTO_ARQUIVO_NAO_ENCONTRADO("MSG-103", 404),
    ERRO_DOCUMENTO_NAO_ACEITO("MSG-104", 400 ),
    ERRO_DOCUMENTO_DOCUMENTO_OBRIGATORIO("MSG-105",400 ),
    ERRO_QUANTIDADE_DOCUMENTO_OBRIGATORIO("MSG-106", 400 ),
    ERRO_INFORMACOES_MATRICULA_NAO_INFORMADA("MSG-107",400),

    //TURMA
    ERRO_SEM_ALUNOS_TURMA("MSG-151", 404),
    ERRO_TURMA_NAO_ENCONTRADA("MSG-152", 404),
    ERRO_ALUNO_JA_ESTA_NA_TURMA("MSG-153",400),
    ERRO_MATRICULA_NAO_ATIVA_PARA_TURMA("MSG-154", 400),

    //MATRICULA
    ERRO_MATRICULA_SEM_RESPONSAVEL("MSG-201", 404),
    ERRO_LISTAR_MATRICULA_STATUS("MSG-202", 404 ),
    ERRO_MATRICULA_NAO_ENCONTRADA("MSG-203", 404),
    ERRO_CPF_REPETIDO_TUTORES_CRIANCA("MSG-204", 400 ),
    ERRO_DATA_NASCIMENTO_ALUNO_DEVE_SER_INFORMADA("MSG-205", 400),
    ERRO_IDADE_ALUNO_ACIMA_DO_PERMITIDO("MSG-206", 400),
    ERRO_MATRICULA_RESPONSAVEL_NASCIMENTO_NAO_INFORMADO("MSG-207", 400 ),
    ERRO_MATRICULA_RESPONSAVEL_MENOR_IDADE("MSG-208", 400 ),
    ERRO_MATRICULA_STATUS_NAO_ATIVA("MSG-209", 400 ),
    ERRO_MATRICULA_ALTERAR_DOCUMENTOS_NAO_ENCONTRADOS("MSG-210",404),

    //TERMO
    ERRO_GERAR_TERMO("MSG-301", 404),

    //CONTROLE PERIODO MATRICULA
    ERRO_CONTROLE_PERIODO_INCLUIR("MSG-401", 400),
    ERRO_CONTROLE_PERIODO_ALTERAR("MSG-402", 400),
    ERRO_CONTROLE_PERIODO_EXCLUIR("MSG-403", 400),
    ERRO_PERIODO_MATRICULA_NAO_ACEITANDO("MSG-404",400 );

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
