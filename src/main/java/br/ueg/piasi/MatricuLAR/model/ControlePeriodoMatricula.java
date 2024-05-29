package br.ueg.piasi.MatricuLAR.model;

import br.ueg.prog.webi.api.model.BaseEntidade;
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
@Table(name = ControlePeriodoMatricula.NOME_TABELA)
@FieldNameConstants
public class ControlePeriodoMatricula extends BaseEntidade<Long> {
    public static final String NOME_TABELA = "controle_periodo_matricula";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "periodo_matricula", nullable = false)
    private Boolean aceitandoCadastroMatricula;

    @Column(name = "inicio_programado")
    private LocalDate dataInicio;

    @Column(name = "fim_programado")
    private LocalDate dataFim;

    @Column(name = "atualizar_periodo_automatico", nullable = false)
    private Boolean atualizarPeriodoAutomatico;
}
