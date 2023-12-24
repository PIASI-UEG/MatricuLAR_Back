package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.enums.StatusMatricula;
import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

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
    @Column(name = "CPF", nullable = false, length = 11,updatable = false)
    @Searchable()
    private String cpf;

    @Column(name = "renda", nullable = false)
    private Double renda;

    @Column(name = "status", length = 11, nullable = false)
    private StatusMatricula status;

    @Column(name = "nascimento", nullable = false)
    private LocalDate nascimento;

    @Column(name = "pais_casados", nullable = false)
    private Boolean paisCasados;

    @Column(name = "pais_moram_juntos", nullable = false)
    private Boolean moramJuntos;

    @Column(name = "observacao", length = 200)
    private String observacao;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco", unique = true, nullable = false,
            referencedColumnName = Endereco.Fields.id,
            foreignKey = @ForeignKey(name = "fk_matricula_endereco"))
    @Searchable()
    private Endereco endereco;
}