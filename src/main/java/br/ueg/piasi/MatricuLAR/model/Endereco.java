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
@Table(name = Endereco.NOME_TABELA)
@FieldNameConstants
public class Endereco extends BaseEntidade<Long> {

    public static final String NOME_TABELA = "endereco";

    @SequenceGenerator(
            name = "endereco_gerador_sequence",
            sequenceName = "endereco_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "endereco_gerador_sequence"

    )

    @Id
    @Column(name = "id")
    @Searchable()
    private Long id;

    @Column(name = "CEP", nullable = false, length = 8)
    @Searchable()
    private String cep;

    @Column(name = "bairro", nullable = false, length = 250)
    @Searchable()
    private String bairro;

    @Column(name = "cidade", nullable = false, length = 250)
    @Searchable()
    private String cidade;

    @Column(name = "logradouro", nullable = false, length = 250)
    @Searchable()
    private String logradouro;

    @Column(name = "complemento", length = 250)
    @Searchable()
    private String complemento;

}