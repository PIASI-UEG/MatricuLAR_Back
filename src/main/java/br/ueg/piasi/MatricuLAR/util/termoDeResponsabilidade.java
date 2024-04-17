package br.ueg.piasi.MatricuLAR.util;

import br.ueg.piasi.MatricuLAR.dto.TurmaDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class termoDeResponsabilidade {
    public static final String JASPER_TERMO = ".\\src\\main\\resources\\sagradaFamiliaTermo_A4.jrxml";

    public static void gerarTermoSemAss(List<TurmaDTO> turmas) {
        try {

            JasperReport report = JasperCompileManager.compileReport(JASPER_TERMO);

            JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), new JREmptyDataSource());

            //CAMINHO ONDE SERÁ SALVO O PDF (por enquanto deixando na pasta fotos)
            JasperExportManager.exportReportToPdfFile(print, ".\\src\\main\\resources\\images\\Termo-Responsabilidade.pdf");
            System.out.println("Gerando pdf");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
