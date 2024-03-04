package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.converter.TurnoConverter;
import br.ueg.piasi.MatricuLAR.enums.Turno;
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
    @Searchable
    private String titulo;

    @Column(name = "nome_professor", length = 200, nullable = false)
    private String nomeProfessor;

    @Column(name = "ano", nullable = false, length = 4)
    private Integer ano;

//    @Temporal(TemporalType.TIME)
    @Column(name = "hora_inicio", nullable = false, length = 4)
    private String horaInicio;

//    @Temporal(TemporalType.TIME)
    @Column(name = "hora_fim", nullable = false, length = 4)
    private String horaFim;

    @Convert(converter = TurnoConverter.class)
    @Column(name = "turno", nullable = false, length = 1)
    private Turno turno;

    @Column(name = "telefone_professora", nullable = false, length = 11)
    private String telefoneProfessor;

    @Transient
    private Long quantidadeAlunos;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "turma",
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private Set<MatriculaTurma> turmaMatriculas = new HashSet<>();

}