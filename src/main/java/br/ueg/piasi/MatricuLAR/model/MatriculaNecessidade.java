package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.model.pkComposta.PkMatriculaNecessidade;
import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = MatriculaNecessidade.NOME_TABELA)
@FieldNameConstants
@IdClass(PkMatriculaNecessidade.class)
public class MatriculaNecessidade extends BaseEntidade<PkMatriculaNecessidade> {

    public static final String NOME_TABELA = "matricula_necessidade";
    public static final String NECESSIDADE_ESPECIAL = "necessidade_id";
    public static final String MATRICULA = "matricula_cpf";

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = MatriculaNecessidade.NECESSIDADE_ESPECIAL, nullable = false,
            referencedColumnName = NecessidadeEspecial.Fields.id,
            foreignKey = @ForeignKey(name = "fk_matricula_necessidade_necessidade"))
    private NecessidadeEspecial necessidadeEspecial;

    @Id
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = MatriculaNecessidade.MATRICULA, nullable = false,
            referencedColumnName = Matricula.Fields.cpf,
            foreignKey = @ForeignKey(name = "fk_matricula_necessidade_matricula") )
    private Matricula matricula;
}
