package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.enums.Turno;
import br.ueg.piasi.MatricuLAR.enums.converter.TurnoConverter;
import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = Turma.NOME_TABELA)
@FieldNameConstants
public class Turma extends BaseEntidade<Long> {

    public static final String NOME_TABELA = "turma";


    @SequenceGenerator(
            name = "turma_gerador_sequence",
            sequenceName = "turma_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "turma_gerador_sequence"
    )

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", length = 100, nullable = false)
    @Searchable(label = "Turma")
    private String titulo;

    @Column(name = "nome_professor", length = 200, nullable = false)
    @Searchable(label = "Nome do(a) Professor(a)")
    private String nomeProfessor;

    @Column(name = "ano", nullable = false, length = 4)
    private Integer ano;

    @Column(name = "hora_inicio", nullable = false, length = 4)
    private String horaInicio;

    @Column(name = "hora_fim", nullable = false, length = 4)
    private String horaFim;

    @Convert(converter = TurnoConverter.class)
    @Column(name = "turno", nullable = false, length = 1)
    private Turno turno;

    @Column(name = "telefone_professora", nullable = false, length = 11)
    private String telefoneProfessor;

    @Transient
    private Long quantidadeAlunos;
}