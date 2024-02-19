package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.model.pkComposta.PkAdvertencia;
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
@Table(name = Advertencia.NOME_TABELA)
@FieldNameConstants
@IdClass(PkAdvertencia.class)
public class Advertencia extends BaseEntidade<PkAdvertencia> {

    public static final String NOME_TABELA = "advertencia";

    public static final String CPF_MATRICULA = "matricula_pessoa_cpf";

    @SequenceGenerator(
            name = "advertencia_gerador_sequence",
            sequenceName = "advertencia_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "advertencia_gerador_sequence"

    )

    @Id
    @Column(name = "numero")
    @Searchable()
    private Long numero;

    @Id
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = Advertencia.CPF_MATRICULA, nullable = false,
            referencedColumnName = Matricula.Fields.cpf,
            foreignKey = @ForeignKey(name = "fk_advertencia_matricula"))
    @Searchable()
    private Matricula matricula;

    @Column(name = "titulo", length = 30, nullable = false)
    private String titulo;

    @Column(name = "descricao", length = 250, nullable = false)
    private String descricao;
}