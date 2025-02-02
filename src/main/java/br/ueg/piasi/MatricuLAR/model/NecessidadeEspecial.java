package br.ueg.piasi.MatricuLAR.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = NecessidadeEspecial.NOME_TABELA)
@FieldNameConstants
public class NecessidadeEspecial extends BaseEntidade<Long> {

    public static final String NOME_TABELA = "necessidade_especial";


    @SequenceGenerator(
            name = "necessidade_especial_gerador_sequence",
            sequenceName = "necessidade_especial_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "necessidade_especial_gerador_sequence"
    )

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "matricula_id", nullable = false,
            referencedColumnName = Matricula.Fields.id,
            foreignKey = @ForeignKey(name="fk_matricula_necessidade"))
    private Matricula matricula;

    @Column(name = "titulo", length = 50, nullable = false)
    @Searchable
    private String titulo;
}
