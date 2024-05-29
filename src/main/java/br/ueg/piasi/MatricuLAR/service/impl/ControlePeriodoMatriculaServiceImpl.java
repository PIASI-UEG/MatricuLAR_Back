package br.ueg.piasi.MatricuLAR.service.impl;

import br.ueg.piasi.MatricuLAR.model.ControlePeriodoMatricula;
import br.ueg.piasi.MatricuLAR.repository.ControlePeriodoMatriculaRepository;
import br.ueg.piasi.MatricuLAR.service.ControlePeriodoMatriculaService;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.stereotype.Service;

import static br.ueg.piasi.MatricuLAR.exception.SistemaMessageCode.*;

@Service
public class ControlePeriodoMatriculaServiceImpl extends BaseCrudService<ControlePeriodoMatricula, Long, ControlePeriodoMatriculaRepository>
implements ControlePeriodoMatriculaService{


    @Override
    protected void prepararParaIncluir(ControlePeriodoMatricula entidade) {

    }

    @Override
    protected void validarDados(ControlePeriodoMatricula entidade) {

    }

    @Override
    protected void validarCamposObrigatorios(ControlePeriodoMatricula entidade) {

    }

    public ControlePeriodoMatricula atualizaPeriodoMatriculaCompleto(ControlePeriodoMatricula controlePeriodoMatricula) {
        controlePeriodoMatricula.setId(1L);
        return repository.saveAndFlush(controlePeriodoMatricula);
    }

    public ControlePeriodoMatricula atualizaPeriodoMatricula(Boolean aceitandoCadastroMatricula) {
        ControlePeriodoMatricula con = repository.findById(1L).get();
        con.setAceitandoCadastroMatricula(aceitandoCadastroMatricula);
        return repository.saveAndFlush(con);
    }

    @Override
    public ControlePeriodoMatricula incluir(ControlePeriodoMatricula modelo) {
        throw new BusinessException(ERRO_CONTROLE_PERIODO_INCLUIR);
    }

    @Override
    public ControlePeriodoMatricula alterar(ControlePeriodoMatricula entidade, Long id) {
        throw new BusinessException(ERRO_CONTROLE_PERIODO_ALTERAR);
    }

    @Override
    public ControlePeriodoMatricula excluir(Long id) {
        throw new BusinessException(ERRO_CONTROLE_PERIODO_EXCLUIR);
    }
}
