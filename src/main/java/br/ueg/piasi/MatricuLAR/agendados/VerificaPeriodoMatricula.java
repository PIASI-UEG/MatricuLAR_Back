package br.ueg.piasi.MatricuLAR.agendados;

import br.ueg.piasi.MatricuLAR.model.ControlePeriodoMatricula;
import br.ueg.piasi.MatricuLAR.service.impl.ControlePeriodoMatriculaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
@EnableScheduling
public class VerificaPeriodoMatricula {

    @Autowired
    private ControlePeriodoMatriculaServiceImpl controlePeriodoMatriculaService;

    /**
     * Executa o metodo automaticamente todos os dias as 2 da manhã
     */
    @Scheduled(cron = "0 0 2 * * *")
    private void verificaPeriodoMatricula() {
        ControlePeriodoMatricula controle = controlePeriodoMatriculaService.obterPeloId(1L);
        if (Objects.nonNull(controle)) {

            verificaSeAtivaPeriodoMatricula(controle);

            verificaSeDesativaPeriodoMatricula(controle);

        }
    }

    /**
     * Verifica se o controle possui data para inicio de recebimento de novas matriculas e se atualização do periodo esta automatico
     * Se sim e a data de inicio for igual a data atual ativa o periodo de recebimento de novas matriculas
     *
     * @param controle variavel que indica se esta no periodo de receber novas matriculas
     */
    private void verificaSeAtivaPeriodoMatricula(ControlePeriodoMatricula controle) {
        if (Objects.nonNull(controle.getDataInicio()) &&
                LocalDate.now().isEqual(controle.getDataInicio()) &&
                !controle.getAceitandoCadastroMatricula() &&
                controle.getAtualizarPeriodoAutomatico()) {

            controlePeriodoMatriculaService.atualizaPeriodoMatricula(true);
        }
    }

    /**
     * Verifica se o controle possui data para fim de recebimento de novas matriculas e se atualização do periodo esta automatico
     * Se sim e a data de fim for igual a data atual, desativa o periodo de recebimento de novas matriculas
     *
     * @param controle variavel que indica se esta no periodo de receber novas matriculas
     */
    private void verificaSeDesativaPeriodoMatricula(ControlePeriodoMatricula controle) {
        if (Objects.nonNull(controle.getDataFim()) &&
                LocalDate.now().isEqual(controle.getDataFim()) &&
                controle.getAceitandoCadastroMatricula() &&
                controle.getAtualizarPeriodoAutomatico()) {

            controlePeriodoMatriculaService.atualizaPeriodoMatricula(false);
        }
    }
}
