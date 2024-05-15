package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.enums.Vinculo;
import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

import static br.ueg.piasi.MatricuLAR.model.Tutor.NOME_TABELA;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = NOME_TABELA)
@FieldNameConstants
public class Tutor extends BaseEntidade<String> {

    public static final String NOME_TABELA = "tutor";

    @Id
    @Searchable (label = "CPF")
    private String cpf;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = Fields.cpf,
            referencedColumnName = Pessoa.Fields.cpf, nullable = false,
            foreignKey = @ForeignKey(name = "fk_tutor_pessoa"))
    private Pessoa pessoa;

    @Column(name = "empresa_telefone_celular", length = 11)
    private String telefoneCelularEmpresarial;

    @Column(name = "empresa_telefone_fixo", length = 11)
    private String telefoneFixoEmpresarial;

    @Column(name = "empresa_cnpj", nullable = false, length = 14)
    private String empresaCnpj;

    @Column(name = "empresa_nome", nullable = false, length = 100)
    private String empresaNome;

    @Column(name = "profissao", nullable = false, length = 50)
    private String profissao;

    @Column(name="casado", nullable = false)
    private Boolean casado;

    @Column(name = "mora_com_conjuge", nullable = false)
    private Boolean moraComConjuge;

    @Temporal(TemporalType.DATE)
    @Column(name = "nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Transient
    private Vinculo vinculo;
}
