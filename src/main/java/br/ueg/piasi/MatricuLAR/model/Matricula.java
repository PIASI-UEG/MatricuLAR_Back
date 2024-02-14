package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = Matricula.NOME_TABELA)
@FieldNameConstants
public class Matricula extends BaseEntidade<String> {

    public static final String NOME_TABELA = "matricula";

    @Id
    @Searchable (label = "CPF")
    private String cpf;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = Fields.cpf,
            referencedColumnName = Pessoa.Fields.cpf, nullable = false,
            foreignKey = @ForeignKey(name = "fk_matricula_pessoa"))
    private Pessoa pessoa;

    @Column(name = "renda", nullable = false)
    private Double renda;

    @Column(name = "status", length = 2, nullable = false)
    private StatusMatricula status;

    @Temporal(TemporalType.DATE)
    @Column(name = "nascimento", nullable = false)
    private LocalDate nascimento;

    @Column(name = "pais_casados", nullable = false)
    private Boolean paisCasados;

    @Column(name = "pais_moram_juntos", nullable = false)
    private Boolean moramJuntos;

    @Column(name = "observacao", length = 200)
    private String observacao;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco", nullable = false,
            referencedColumnName = Endereco.Fields.id,
            foreignKey = @ForeignKey(name = "fk_matricula_endereco"))
    @Searchable()
    private Endereco endereco;

    @ManyToMany
    @JoinTable(name="matricula_necessidade", joinColumns=
            {@JoinColumn(name="matricula_cpf")}, inverseJoinColumns=
            {@JoinColumn(name="necessidade_id")})
    private Set<NecessidadeEspecial> necessidades = new HashSet<>();


    @OneToMany(mappedBy = "matricula", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<MatriculaTurma> turmas = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "matricula", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Responsavel> responsaveis = new HashSet<>();
}