package br.ueg.piasi.MatricuLAR.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = Pessoa.NOME_TABELA)
public class Pessoa extends BaseEntidade<String> {

    public static final String NOME_TABELA = "pessoa";

    public static final class Coluna{

        public static final String NOME = "pess_nome";
        public static final String CPF = "pess_cpf";
        public static final String FONE = "pess_fone";

    }

    @Id
    @Column(name = Coluna.CPF, nullable = false, length = 11, updatable = false)
    private String cpf;

    @Column(name = Coluna.NOME, nullable = false, length = 200)
    @Searchable()
    private String nome;

    @Column(name = Coluna.FONE, nullable = false)
    private String fone;

}
