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
@Table(name = Advertencia.NOME_TABELA)
@FieldNameConstants
public class Advertencia extends BaseEntidade<Long> {

    public static final String NOME_TABELA = "advertencia";

    @Id
    @Column(name = "CPF", nullable = false, length = 11,updatable = false)
    @Searchable()
    private String cpf;

    @Column(name = "nome", nullable = false, length = 200)
    @Searchable()
    private String nome;

    @Column(name = "telefone", length = 11)
    private String telefone;

}