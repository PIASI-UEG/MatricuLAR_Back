package br.ueg.piasi.MatricuLAR.dto;

import br.ueg.piasi.MatricuLAR.enums.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoMatriculaDTO {

    private TipoDocumento tipoDocumento;

    private Long idMatricula;

    private Boolean aceito;

    private String caminhoDocumento;
}
