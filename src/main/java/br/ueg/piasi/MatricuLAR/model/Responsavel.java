package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.enums.Vinculo;
import br.ueg.piasi.MatricuLAR.model.pkComposta.PkResponsavel;
import br.ueg.prog.webi.api.model.BaseEntidade;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.security.PublicKey;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = Responsavel.NOME_TABELA)
@FieldNameConstants
@IdClass(PkResponsavel.class)
public class Responsavel extends BaseEntidade<PkResponsavel> {

    public static final String NOME_TABELA = "responsavel";
    public static final String ID_MATRICULA = "matricula_id";
    public static final String CPF_RESPONSAVEL = "pessoa_cpf";

    @Id
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = Responsavel.ID_MATRICULA, nullable = false,
            referencedColumnName = Matricula.Fields.id,
            foreignKey = @ForeignKey(name = "fk_responsavel_matricula"))
    private Matricula matricula;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = Responsavel.CPF_RESPONSAVEL, nullable = false,
            referencedColumnName = Pessoa.Fields.cpf,
            foreignKey = @ForeignKey(name = "fk_responsavel_pessoa"))
    private Pessoa pessoa;

    @Column(name = "vinculo", length = 2, nullable = false)
    private Vinculo vinculo;

    @Column(name = "tutor", nullable = false)
    private Boolean tutor;

    @Column(name = "chavePub")
    private PublicKey chavePub;
}
