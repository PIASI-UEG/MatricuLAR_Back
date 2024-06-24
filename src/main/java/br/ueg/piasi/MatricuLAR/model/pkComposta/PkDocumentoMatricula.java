package br.ueg.piasi.MatricuLAR.model.pkComposta;

import br.ueg.prog.webi.api.model.annotation.PkComposite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@PkComposite
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PkDocumentoMatricula implements Serializable {

    protected Long matricula;
    protected String idTipoDocumento;

}
