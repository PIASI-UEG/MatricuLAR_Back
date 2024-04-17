package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.InformacoesMatriculaDTO;
import br.ueg.piasi.MatricuLAR.model.InformacoesMatricula;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.prog.webi.api.model.IEntidade;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-17T19:26:56-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class InformacoesMatriculaMapperImpl implements InformacoesMatriculaMapper {

    @Override
    public List<InformacoesMatriculaDTO> toDTO(List<InformacoesMatricula> lista) {
        if ( lista == null ) {
            return null;
        }

        List<InformacoesMatriculaDTO> list = new ArrayList<InformacoesMatriculaDTO>( lista.size() );
        for ( InformacoesMatricula informacoesMatricula : lista ) {
            list.add( toDTO( informacoesMatricula ) );
        }

        return list;
    }

    @Override
    public void updateModel(InformacoesMatricula source, InformacoesMatricula target) {
        if ( source == null ) {
            return;
        }

        if ( source.getCompositePkEntidadeObject() != null ) {
            target.setCompositePkEntidadeObject( source.getCompositePkEntidadeObject() );
        }
        if ( target.getForeignEntitiesMaps() != null ) {
            Map<String, IEntidade<?>> map = source.getForeignEntitiesMaps();
            if ( map != null ) {
                target.getForeignEntitiesMaps().clear();
                target.getForeignEntitiesMaps().putAll( map );
            }
        }
        else {
            Map<String, IEntidade<?>> map = source.getForeignEntitiesMaps();
            if ( map != null ) {
                target.setForeignEntitiesMaps( new LinkedHashMap<String, IEntidade<?>>( map ) );
            }
        }
        if ( source.getId() != null ) {
            target.setId( source.getId() );
        }
        if ( source.getMatricula() != null ) {
            target.setMatricula( source.getMatricula() );
        }
        if ( source.getEsteveOutraCreche() != null ) {
            target.setEsteveOutraCreche( source.getEsteveOutraCreche() );
        }
        if ( source.getRazaoSaidaCreche() != null ) {
            target.setRazaoSaidaCreche( source.getRazaoSaidaCreche() );
        }
        if ( source.getTipoResidencia() != null ) {
            target.setTipoResidencia( source.getTipoResidencia() );
        }
        if ( source.getValorAluguel() != null ) {
            target.setValorAluguel( source.getValorAluguel() );
        }
        if ( source.getBeneficiarioGoverno() != null ) {
            target.setBeneficiarioGoverno( source.getBeneficiarioGoverno() );
        }
        if ( source.getValorBeneficio() != null ) {
            target.setValorBeneficio( source.getValorBeneficio() );
        }
        if ( source.getRendaFamiliar() != null ) {
            target.setRendaFamiliar( source.getRendaFamiliar() );
        }
        if ( source.getPaisCasados() != null ) {
            target.setPaisCasados( source.getPaisCasados() );
        }
        if ( source.getMoramJuntos() != null ) {
            target.setMoramJuntos( source.getMoramJuntos() );
        }
        if ( source.getObservacao() != null ) {
            target.setObservacao( source.getObservacao() );
        }
    }

    @Override
    public InformacoesMatricula toModelo(InformacoesMatriculaDTO DTO) {
        if ( DTO == null ) {
            return null;
        }

        InformacoesMatricula.InformacoesMatriculaBuilder informacoesMatricula = InformacoesMatricula.builder();

        informacoesMatricula.matricula( informacoesMatriculaDTOToMatricula( DTO ) );
        informacoesMatricula.id( DTO.getId() );
        informacoesMatricula.esteveOutraCreche( DTO.getEsteveOutraCreche() );
        informacoesMatricula.razaoSaidaCreche( DTO.getRazaoSaidaCreche() );
        informacoesMatricula.tipoResidencia( DTO.getTipoResidencia() );
        informacoesMatricula.valorAluguel( DTO.getValorAluguel() );
        informacoesMatricula.beneficiarioGoverno( DTO.getBeneficiarioGoverno() );
        informacoesMatricula.valorBeneficio( DTO.getValorBeneficio() );
        informacoesMatricula.rendaFamiliar( DTO.getRendaFamiliar() );
        informacoesMatricula.paisCasados( DTO.getPaisCasados() );
        informacoesMatricula.moramJuntos( DTO.getMoramJuntos() );
        informacoesMatricula.observacao( DTO.getObservacao() );

        return informacoesMatricula.build();
    }

    @Override
    public InformacoesMatriculaDTO toDTO(InformacoesMatricula modelo) {
        if ( modelo == null ) {
            return null;
        }

        InformacoesMatriculaDTO.InformacoesMatriculaDTOBuilder informacoesMatriculaDTO = InformacoesMatriculaDTO.builder();

        informacoesMatriculaDTO.id( modeloMatriculaId( modelo ) );
        informacoesMatriculaDTO.esteveOutraCreche( modelo.getEsteveOutraCreche() );
        informacoesMatriculaDTO.razaoSaidaCreche( modelo.getRazaoSaidaCreche() );
        informacoesMatriculaDTO.tipoResidencia( modelo.getTipoResidencia() );
        informacoesMatriculaDTO.valorAluguel( modelo.getValorAluguel() );
        informacoesMatriculaDTO.beneficiarioGoverno( modelo.getBeneficiarioGoverno() );
        informacoesMatriculaDTO.valorBeneficio( modelo.getValorBeneficio() );
        informacoesMatriculaDTO.rendaFamiliar( modelo.getRendaFamiliar() );
        informacoesMatriculaDTO.paisCasados( modelo.getPaisCasados() );
        informacoesMatriculaDTO.moramJuntos( modelo.getMoramJuntos() );
        informacoesMatriculaDTO.observacao( modelo.getObservacao() );

        return informacoesMatriculaDTO.build();
    }

    protected Matricula informacoesMatriculaDTOToMatricula(InformacoesMatriculaDTO informacoesMatriculaDTO) {
        if ( informacoesMatriculaDTO == null ) {
            return null;
        }

        Matricula.MatriculaBuilder matricula = Matricula.builder();

        matricula.id( informacoesMatriculaDTO.getId() );

        return matricula.build();
    }

    private Long modeloMatriculaId(InformacoesMatricula informacoesMatricula) {
        if ( informacoesMatricula == null ) {
            return null;
        }
        Matricula matricula = informacoesMatricula.getMatricula();
        if ( matricula == null ) {
            return null;
        }
        Long id = matricula.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
