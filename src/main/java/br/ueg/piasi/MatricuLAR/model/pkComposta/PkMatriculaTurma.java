package br.ueg.piasi.MatricuLAR.model.pkComposta;

import br.ueg.prog.webi.api.model.annotation.PkComposite;
import lombok.Data;

import java.io.Serializable;

@PkComposite
@Data
public class PkMatriculaTurma implements Serializable {

    protected Long turma;
    protected String matricula;
}
