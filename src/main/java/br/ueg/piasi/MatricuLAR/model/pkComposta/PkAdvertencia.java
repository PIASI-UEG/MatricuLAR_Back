package br.ueg.piasi.MatricuLAR.model.pkComposta;

import br.ueg.prog.webi.api.model.annotation.PkComposite;
import lombok.Data;

import java.io.Serializable;

@PkComposite
@Data
public class PkAdvertencia implements Serializable {

    protected Long matricula;
    protected Long numero;
}
