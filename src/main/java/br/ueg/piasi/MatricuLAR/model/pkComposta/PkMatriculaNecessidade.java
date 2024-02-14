package br.ueg.piasi.MatricuLAR.model.pkComposta;

import br.ueg.prog.webi.api.model.annotation.PkComposite;
import lombok.Data;

import java.io.Serializable;

@PkComposite
@Data
public class PkMatriculaNecessidade implements Serializable {

    protected Long necessidadeEspecial;
    protected String matricula;
}
