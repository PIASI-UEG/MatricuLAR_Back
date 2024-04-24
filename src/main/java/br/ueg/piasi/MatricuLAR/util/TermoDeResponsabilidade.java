package br.ueg.piasi.MatricuLAR.util;

import br.ueg.piasi.MatricuLAR.dto.AssinaturaDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TermoDeResponsabilidade {
    public static final String JASPER_TERMO = ".\\src\\main\\resources\\sagradaFamiliaTermo_A4.jrxml";

    public static List<AssinaturaDTO> gerarTermoSemAss(List<AssinaturaDTO> assinatura) {
        try {
            System.out.println("gerando termo");
            Map<String, Object> parametros = new HashMap<String, Object>();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(assinatura);

            JasperReport report = JasperCompileManager.compileReport(JASPER_TERMO);

            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

            //CAMINHO ONDE SERÁ SALVO O PDF (por enquanto deixando na pasta fotos)
            JasperExportManager.exportReportToPdfFile(print, ".\\src\\main\\resources\\images\\Termo-Responsabilidade-"+assinatura.get(0).getCPFAss()+".pdf");
            System.out.println("Gerando pdf");

            //termo assinado
            File termo = new File(".\\src\\main\\resources\\images\\Termo-Responsabilidade-"+assinatura.get(0).getCPFAss()+".pdf");

            //termo de teste
            File testeTermoErrado = new File("C:\\Users\\lucas\\Downloads\\termo.pdf");

            DestinatarioAssiDig destinatario = new DestinatarioAssiDig();

            RemetenteAssiDig remetente = new RemetenteAssiDig();

            //assina o termo
            byte[] assinaturas = remetente.geraAssinatura(termo, assinatura.get(0));

            //verifica a assinatura do termo
            destinatario.recebeMensagem(remetente.getPubKey(), testeTermoErrado, assinaturas);


        } catch (Exception e) {
            System.out.println(e);
        }
        return assinatura;
    }

}
