package br.ueg.piasi.MatricuLAR.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
import br.ueg.prog.webi.api.model.annotation.Searchable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@Table(name = Usuario.NOME_TABELA)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends BaseEntidade<Long> {

    public static final String NOME_TABELA = "usuario";

    public static final class Coluna {
        public static final String ID = "usuo_id";
        public static final String LOGIN = "usuo_login";
        public static final String SENHA = "usuo_senha";
        public static final String CPF_PESSOA = "usuo_pessoa";
        public static final String CARGO = "usuo_carg";


    }

    @SequenceGenerator(
            name = "usuario_gerador_sequence",
            sequenceName = "usuario_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "usuario_gerador_sequence"

    )

    @Id
    @Column(name = Coluna.ID)
    @Searchable(label = "Código")
    private Long id;

    @Column(name = Coluna.SENHA, nullable = false)
    private String senha;

    @Column(name = Coluna.CARGO, nullable = false)
    private String cargo;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = Coluna.CPF_PESSOA, unique = true, nullable = false,
            referencedColumnName = Pessoa.Coluna.CPF,
            foreignKey = @ForeignKey(name = "fk_usuario_pessoa"))
    @Searchable()
    private Pessoa pessoa;
}
