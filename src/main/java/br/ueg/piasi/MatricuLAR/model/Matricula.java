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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = Matricula.NOME_TABELA)
@FieldNameConstants
public class Matricula extends BaseEntidade<Long> {

    public static final String NOME_TABELA = "matricula";

    @Id
    @Column(name = "CPF", nullable = false, length = 11,updatable = false)
    @Searchable()
    private Long cpf;

    @Column(name = "nome", nullable = false, length = 200)
    @Searchable()
    private String nome;

    @Column(name = "telefone", length = 11)
    private String telefone;

}