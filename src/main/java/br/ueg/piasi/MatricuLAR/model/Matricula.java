package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = Matricula.NOME_TABELA)
@FieldNameConstants
public class Matricula extends BaseEntidade<Long> {

    public static final String NOME_TABELA = "matricula";

    @SequenceGenerator(
            name = "matricula_gerador_sequence",
            sequenceName = "matricula_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "matricula_gerador_sequence"

    )

    @Id
    @Column(name = "id")
    @Searchable(label = "Número da matrícula")
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_cpf",
            referencedColumnName = Pessoa.Fields.cpf, nullable = false,
            foreignKey = @ForeignKey(name = "fk_matricula_pessoa"))
    @Searchable(foreignEntityFieldTarget = "nome", foreignEntityFieldLabel = "Nome do(a) aluno(a)")
    private Pessoa pessoa;

    @Column(name = "status", length = 2, nullable = false)
    @Searchable()
    private StatusMatricula status;

    @Temporal(TemporalType.DATE)
    @Column(name = "nascimento", nullable = false)
    private LocalDate nascimento;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco", nullable = false,
            referencedColumnName = Endereco.Fields.id,
            foreignKey = @ForeignKey(name = "fk_matricula_endereco"))
    //@Searchable()
    private Endereco endereco;


    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "matricula", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NecessidadeEspecial> necessidades = new HashSet<>();

   @ManyToOne
   @JoinColumn(name = "turma",
           referencedColumnName = Turma.Fields.id,
           foreignKey = @ForeignKey(name = "fk_matricula_tuma"))
   @Searchable(foreignEntityFieldTarget = "titulo", foreignEntityFieldLabel = "Turma")
    private Turma turma;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "matricula", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Responsavel> responsaveis = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "matricula",fetch = FetchType.EAGER,
            orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    //@Searchable(label = "Advertências")
    private Set<Advertencia> advertencias = new HashSet<>();

    @OneToOne(mappedBy = "matricula", cascade = CascadeType.ALL)
    private InformacoesMatricula informacoesMatricula;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<DocumentoMatricula> documentoMatricula = new HashSet<>();

    @Transient
    private List<Tutor> tutorList;
}