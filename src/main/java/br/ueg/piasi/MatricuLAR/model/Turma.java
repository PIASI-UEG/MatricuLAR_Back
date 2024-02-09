package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.enums.Turno;
import br.ueg.piasi.MatricuLAR.converter.TurnoConverter;
import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "ano", nullable = false)
    private LocalDate ano;

    @Temporal(TemporalType.TIME)
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Temporal(TemporalType.TIME)
    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @Convert(converter = TurnoConverter.class)
    @Column(name = "turno", nullable = false, length = 1)
    private Turno turno;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "turma",
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private Set<MatriculaTurma> turmaMatriculas = new HashSet<>();

}