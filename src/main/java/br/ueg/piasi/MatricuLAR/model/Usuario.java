package br.ueg.piasi.MatricuLAR.model;

import br.ueg.piasi.MatricuLAR.enums.Cargo;
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
@FieldNameConstants
public class Usuario extends BaseEntidade<Long> {

    public static final String NOME_TABELA = "usuario";

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
    @Column(name = "id")
    @Searchable(label = "Código")
    private Long id;

    @Column(name = "senha", nullable = false, length = 200)
    private String senha;

    @Column(name = "cargo", nullable = false, length = 45)
    @Searchable()
    private Cargo cargo;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_cpf", unique = true, nullable = false,
            referencedColumnName = Pessoa.Fields.cpf,
            foreignKey = @ForeignKey(name = "fk_usuario_pessoa"))
    @Searchable()
    private Pessoa pessoa;
}
