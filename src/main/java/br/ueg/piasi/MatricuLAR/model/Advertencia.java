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
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "matricula_id", nullable = false,
            referencedColumnName = Matricula.Fields.id,
            foreignKey = @ForeignKey(name = "fk_advertencia_matricula"))
    private Matricula matricula;

    @Column(name = "titulo", length = 30, nullable = false)
    private String titulo;

    @Column(name = "descricao", length = 250, nullable = false)
    private String descricao;
}