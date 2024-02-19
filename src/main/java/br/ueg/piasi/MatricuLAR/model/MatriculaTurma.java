package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.model.pkComposta.PkMatriculaTurma;
import br.ueg.prog.webi.api.model.BaseEntidade;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = MatriculaTurma.NOME_TABELA)
@FieldNameConstants
@IdClass(PkMatriculaTurma.class)
public class MatriculaTurma extends BaseEntidade<PkMatriculaTurma> {

    public static final String NOME_TABELA = "matricula_turma";
    public static final String TURMA = "turma_id";
    public static final String MATRICULA = "matricula_cpf";

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = MatriculaTurma.TURMA,
                referencedColumnName = "id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_matricula_turma_turma"))
    private Turma turma;

    @Id
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = MatriculaTurma.MATRICULA,
                referencedColumnName = Matricula.Fields.cpf, nullable = false,
                foreignKey = @ForeignKey(name = "fk_matricula_turma_matricula"))
    private Matricula matricula;

    @Temporal(TemporalType.DATE)
    @Column(name = "entrada", nullable = false)
    private LocalDate entradaTurma;
}
