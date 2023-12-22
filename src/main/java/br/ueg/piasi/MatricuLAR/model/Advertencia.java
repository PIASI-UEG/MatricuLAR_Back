package br.ueg.piasi.MatricuLAR.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = Advertencia.NOME_TABELA)
@FieldNameConstants
public class Advertencia extends BaseEntidade<Long> {

    public static final String NOME_TABELA = "advertencia";

    public static final String CPF_MATRICULA = "adv_matricula";

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
    @Column(name = "id")
    @Searchable()
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = Advertencia.CPF_MATRICULA, unique = true, nullable = false,
            referencedColumnName = Matricula.Fields.cpf,
            foreignKey = @ForeignKey(name = "fk_advertencia_matricula"))
    @Searchable()
    private Matricula matricula;

    @Column(name = "titulo", length = 30, nullable = false)
    private String titulo;

    @Column(name = "descricao", length = 250, nullable = false)
    private String descricao;
}