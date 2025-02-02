package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.model.pkComposta.PkDocumentoMatricula;
import br.ueg.prog.webi.api.model.BaseEntidade;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = DocumentoMatricula.NOME_TABELA)
@IdClass(PkDocumentoMatricula.class)
public class DocumentoMatricula extends BaseEntidade<PkDocumentoMatricula> {

    public static final String NOME_TABELA = "documento_matricula";
    public static final String MATRICULA_ID = "matricula_id";

    @Id
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = MATRICULA_ID, nullable = false,
            referencedColumnName = Matricula.Fields.id,
            foreignKey = @ForeignKey(name = "fk_documento_matricula"))
    private Matricula matricula;

    @Id
    @Column(name = "enum_doc", length = 5, nullable = false)
    private String idTipoDocumento;

    @Column(name = "aceito", nullable = false)
    private Boolean aceito;

    @Column(name = "caminho", nullable = false, length = 300)
    private String caminhoDocumento;
}
