package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.enums.TipoResidencia;
import br.ueg.prog.webi.api.model.BaseEntidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = InformacoesMatricula.NOME_TABELA)
@FieldNameConstants
public class InformacoesMatricula extends BaseEntidade<Long> {


    public static final String NOME_TABELA = "informacoes";

    @Id
    private Long id;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = Fields.id,
            referencedColumnName = Matricula.Fields.id, nullable = false,
            foreignKey = @ForeignKey(name = "fk_informacoes_matricula"))
    private Matricula matricula;

    @Column(name = "esteve_em_outra_creche", nullable = false)
    private Boolean frequentouOutraCreche;

    @Column(name = "razao_saida_creche", length = 200)
    private String razaoSaida;

    @Column(name = "tipo_residencia", nullable = false)
    private String tipoResidencia;

    @Column(name = "valor_aluguel")
    private Long valorAluguel;

    @Column(name = "beneficiario", nullable = false)
    private Boolean possuiBeneficiosDoGoverno;

    @Column(name = "valor_beneficio")
    private Long valorBeneficio;

    @Column(name = "renda_familiar", nullable = false)
    private BigDecimal rendaFamiliar;

    @Column(name = "observacao", length = 200)
    private String observacao;


}
