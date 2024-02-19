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

    @Column(name = "empresa_telefone", nullable = false, length = 11)
    private String empresaTelefone;

    @Column(name = "empresa_cnpj", nullable = false, length = 14)
    private String empresaCnpj;

    @Column(name = "empresa_nome", nullable = false, length = 100)
    private String empresaNome;

    @Column(name = "profissao", nullable = false, length = 50)
    private String profissao;

    @Column(name = "whatsapp", nullable = false)
    private Boolean telefoneWhatsapp;

    @Transient
    private Vinculo vinculo;
}
