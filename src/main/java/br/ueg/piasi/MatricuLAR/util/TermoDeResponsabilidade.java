package br.ueg.piasi.MatricuLAR.util;

import br.ueg.piasi.MatricuLAR.dto.AssinaturaDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.ResponsavelDTO;
import br.ueg.piasi.MatricuLAR.mapper.MatriculaMapper;
import br.ueg.piasi.MatricuLAR.mapper.MatriculaMapperImpl;
import br.ueg.piasi.MatricuLAR.model.Endereco;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Responsavel;
import br.ueg.piasi.MatricuLAR.service.impl.MatriculaServiceImpl;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.security.PublicKey;
import java.util.*;

@Component
public class TermoDeResponsabilidade {
    public static final String JASPER_TERMO_ASSINADO = ".\\src\\main\\resources\\sagradaFamiliaTermo_A4.jrxml";

    public static final String JASPER_TERMO = ".\\src\\main\\resources\\termoSemAssinatura.jrxml";

    @Autowired
    private MatriculaServiceImpl matriculaService;

    @Autowired
    private MatriculaMapper matriculaMapper;

    public List<AssinaturaDTO> gerarTermoSemAss(String imgAss, Long idMatricula) {
        try {
            System.out.println("gerando termo");
            List<AssinaturaDTO> assinatura = preencheDTO(imgAss, idMatricula);

            Map<String, Object> parametros = new HashMap<String, Object>();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(assinatura);


            JasperReport report = JasperCompileManager.compileReport(JASPER_TERMO);

            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

            //CAMINHO ONDE SERÁ SALVO O PDF (por enquanto deixando na pasta fotos)
            JasperExportManager.exportReportToPdfFile(print, ".\\src\\main\\resources\\images\\Termo-Responsabilidade-"+assinatura.get(0).getCpfCrianca()+".pdf");
            System.out.println("Gerando pdf");

            return assinatura;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private List<AssinaturaDTO> preencheDTO(String imgAss, Long idMatricula){
        List<AssinaturaDTO> assinatura = new ArrayList<>();
        Matricula matricula = matriculaService.obterPeloId(idMatricula);
        MatriculaDTO matriculaDTO = matriculaMapper.toDTO(matricula);
        Endereco endereco = matricula.getEndereco();
        //TODO ver como obter responsavel do set
        ResponsavelDTO responsavel = matriculaDTO.getResponsaveis().get(0);
        assinatura.add(AssinaturaDTO.builder()
                        .imagemAss(imgAss)
                        .endereco(endereco.getLogradouro()+", "+endereco.getComplemento()+", "+endereco.getBairro())
                        .nomeResponsavel(responsavel.getNomeResponsavel())
                        .cpfCrianca(matricula.getPessoa().getCpf())
                        .cpfResponsavel(responsavel.getCpfResponsavel())
                .build());
        return assinatura;
    }
}
