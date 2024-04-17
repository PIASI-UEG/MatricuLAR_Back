package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.AdvertenciaDTO;
import br.ueg.piasi.MatricuLAR.dto.DocumentoMatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.InformacoesMatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.MatriculaDTO;
import br.ueg.piasi.MatricuLAR.dto.NecessidadeEspecialDTO;
import br.ueg.piasi.MatricuLAR.dto.ResponsavelDTO;
import br.ueg.piasi.MatricuLAR.dto.TurmaDTO;
import br.ueg.piasi.MatricuLAR.dto.TutorDTO;
import br.ueg.piasi.MatricuLAR.model.Advertencia;
import br.ueg.piasi.MatricuLAR.model.DocumentoMatricula;
import br.ueg.piasi.MatricuLAR.model.Endereco;
import br.ueg.piasi.MatricuLAR.model.InformacoesMatricula;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.model.NecessidadeEspecial;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Responsavel;
import br.ueg.piasi.MatricuLAR.model.Turma;
import br.ueg.piasi.MatricuLAR.model.Tutor;
import br.ueg.prog.webi.api.model.IEntidade;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-17T19:26:55-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class MatriculaMapperImpl implements MatriculaMapper {

    @Autowired
    private ResponsavelMapperImpl responsavelMapperImpl;
    @Autowired
    private NecessidadeEspecialMapperImpl necessidadeEspecialMapperImpl;
    @Autowired
    private TutorMapperImpl tutorMapperImpl;
    @Autowired
    private DocumentoMatriculaMapperImpl documentoMatriculaMapperImpl;

    @Override
    public List<MatriculaDTO> toDTO(List<Matricula> lista) {
        if ( lista == null ) {
            return null;
        }

        List<MatriculaDTO> list = new ArrayList<MatriculaDTO>( lista.size() );
        for ( Matricula matricula : lista ) {
            list.add( toDTO( matricula ) );
        }

        return list;
    }

    @Override
    public void updateModel(Matricula source, Matricula target) {
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
        if ( source.getPessoa() != null ) {
            target.setPessoa( source.getPessoa() );
        }
        if ( source.getStatus() != null ) {
            target.setStatus( source.getStatus() );
        }
        if ( source.getNascimento() != null ) {
            target.setNascimento( source.getNascimento() );
        }
        if ( source.getEndereco() != null ) {
            target.setEndereco( source.getEndereco() );
        }
        if ( target.getNecessidades() != null ) {
            Set<NecessidadeEspecial> set = source.getNecessidades();
            if ( set != null ) {
                target.getNecessidades().clear();
                target.getNecessidades().addAll( set );
            }
        }
        else {
            Set<NecessidadeEspecial> set = source.getNecessidades();
            if ( set != null ) {
                target.setNecessidades( new LinkedHashSet<NecessidadeEspecial>( set ) );
            }
        }
        if ( source.getTurma() != null ) {
            target.setTurma( source.getTurma() );
        }
        if ( target.getResponsaveis() != null ) {
            Set<Responsavel> set1 = source.getResponsaveis();
            if ( set1 != null ) {
                target.getResponsaveis().clear();
                target.getResponsaveis().addAll( set1 );
            }
        }
        else {
            Set<Responsavel> set1 = source.getResponsaveis();
            if ( set1 != null ) {
                target.setResponsaveis( new LinkedHashSet<Responsavel>( set1 ) );
            }
        }
        if ( target.getAdvertencias() != null ) {
            Set<Advertencia> set2 = source.getAdvertencias();
            if ( set2 != null ) {
                target.getAdvertencias().clear();
                target.getAdvertencias().addAll( set2 );
            }
        }
        else {
            Set<Advertencia> set2 = source.getAdvertencias();
            if ( set2 != null ) {
                target.setAdvertencias( new LinkedHashSet<Advertencia>( set2 ) );
            }
        }
        if ( source.getInformacoesMatricula() != null ) {
            target.setInformacoesMatricula( source.getInformacoesMatricula() );
        }
        if ( target.getDocumentoMatricula() != null ) {
            Set<DocumentoMatricula> set3 = source.getDocumentoMatricula();
            if ( set3 != null ) {
                target.getDocumentoMatricula().clear();
                target.getDocumentoMatricula().addAll( set3 );
            }
        }
        else {
            Set<DocumentoMatricula> set3 = source.getDocumentoMatricula();
            if ( set3 != null ) {
                target.setDocumentoMatricula( new LinkedHashSet<DocumentoMatricula>( set3 ) );
            }
        }
        if ( target.getTutorList() != null ) {
            List<Tutor> list = source.getTutorList();
            if ( list != null ) {
                target.getTutorList().clear();
                target.getTutorList().addAll( list );
            }
        }
        else {
            List<Tutor> list = source.getTutorList();
            if ( list != null ) {
                target.setTutorList( new ArrayList<Tutor>( list ) );
            }
        }
    }

    @Override
    public MatriculaDTO toDTO(Matricula modelo) {
        if ( modelo == null ) {
            return null;
        }

        MatriculaDTO.MatriculaDTOBuilder matriculaDTO = MatriculaDTO.builder();

        matriculaDTO.enderecoId( modeloEnderecoId( modelo ) );
        matriculaDTO.nome( modeloPessoaNome( modelo ) );
        matriculaDTO.cpf( modeloPessoaCpf( modelo ) );
        matriculaDTO.turma( turmaToTurmaDTO( modelo.getTurma() ) );
        matriculaDTO.id( modelo.getId() );
        matriculaDTO.status( modelo.getStatus() );
        matriculaDTO.nascimento( modelo.getNascimento() );
        matriculaDTO.necessidades( necessidadeEspecialSetToNecessidadeEspecialDTOList( modelo.getNecessidades() ) );
        matriculaDTO.responsaveis( responsavelSetToResponsavelDTOList( modelo.getResponsaveis() ) );
        matriculaDTO.advertencias( advertenciaSetToAdvertenciaDTOList( modelo.getAdvertencias() ) );
        matriculaDTO.informacoesMatricula( informacoesMatriculaToInformacoesMatriculaDTO( modelo.getInformacoesMatricula() ) );
        matriculaDTO.documentoMatricula( documentoMatriculaSetToDocumentoMatriculaDTOList( modelo.getDocumentoMatricula() ) );

        return matriculaDTO.build();
    }

    @Override
    public Matricula toModelo(MatriculaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Matricula.MatriculaBuilder matricula = Matricula.builder();

        matricula.endereco( matriculaDTOToEndereco( dto ) );
        matricula.pessoa( matriculaDTOToPessoa( dto ) );
        matricula.turma( turmaDTOToTurma( dto.getTurma() ) );
        matricula.tutorList( tutorDTOListToTutorList( dto.getTutorDTOList() ) );
        matricula.id( dto.getId() );
        matricula.status( dto.getStatus() );
        matricula.nascimento( dto.getNascimento() );
        matricula.necessidades( necessidadeEspecialDTOListToNecessidadeEspecialSet( dto.getNecessidades() ) );
        matricula.responsaveis( responsavelDTOListToResponsavelSet( dto.getResponsaveis() ) );
        matricula.advertencias( advertenciaDTOListToAdvertenciaSet( dto.getAdvertencias() ) );
        matricula.informacoesMatricula( informacoesMatriculaDTOToInformacoesMatricula( dto.getInformacoesMatricula() ) );
        matricula.documentoMatricula( documentoMatriculaDTOListToDocumentoMatriculaSet( dto.getDocumentoMatricula() ) );

        return matricula.build();
    }

    private Long modeloEnderecoId(Matricula matricula) {
        if ( matricula == null ) {
            return null;
        }
        Endereco endereco = matricula.getEndereco();
        if ( endereco == null ) {
            return null;
        }
        Long id = endereco.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String modeloPessoaNome(Matricula matricula) {
        if ( matricula == null ) {
            return null;
        }
        Pessoa pessoa = matricula.getPessoa();
        if ( pessoa == null ) {
            return null;
        }
        String nome = pessoa.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }

    private String modeloPessoaCpf(Matricula matricula) {
        if ( matricula == null ) {
            return null;
        }
        Pessoa pessoa = matricula.getPessoa();
        if ( pessoa == null ) {
            return null;
        }
        String cpf = pessoa.getCpf();
        if ( cpf == null ) {
            return null;
        }
        return cpf;
    }

    protected TurmaDTO turmaToTurmaDTO(Turma turma) {
        if ( turma == null ) {
            return null;
        }

        TurmaDTO turmaDTO = new TurmaDTO();

        turmaDTO.setId( turma.getId() );
        turmaDTO.setTitulo( turma.getTitulo() );
        turmaDTO.setNomeProfessor( turma.getNomeProfessor() );
        turmaDTO.setTurno( turma.getTurno() );
        turmaDTO.setAno( turma.getAno() );
        turmaDTO.setHoraInicio( turma.getHoraInicio() );
        turmaDTO.setHoraFim( turma.getHoraFim() );
        turmaDTO.setTelefoneProfessor( turma.getTelefoneProfessor() );
        turmaDTO.setQuantidadeAlunos( turma.getQuantidadeAlunos() );

        return turmaDTO;
    }

    protected List<NecessidadeEspecialDTO> necessidadeEspecialSetToNecessidadeEspecialDTOList(Set<NecessidadeEspecial> set) {
        if ( set == null ) {
            return null;
        }

        List<NecessidadeEspecialDTO> list = new ArrayList<NecessidadeEspecialDTO>( set.size() );
        for ( NecessidadeEspecial necessidadeEspecial : set ) {
            list.add( necessidadeEspecialMapperImpl.toDTO( necessidadeEspecial ) );
        }

        return list;
    }

    protected List<ResponsavelDTO> responsavelSetToResponsavelDTOList(Set<Responsavel> set) {
        if ( set == null ) {
            return null;
        }

        List<ResponsavelDTO> list = new ArrayList<ResponsavelDTO>( set.size() );
        for ( Responsavel responsavel : set ) {
            list.add( responsavelMapperImpl.toDTO( responsavel ) );
        }

        return list;
    }

    protected AdvertenciaDTO advertenciaToAdvertenciaDTO(Advertencia advertencia) {
        if ( advertencia == null ) {
            return null;
        }

        AdvertenciaDTO.AdvertenciaDTOBuilder advertenciaDTO = AdvertenciaDTO.builder();

        advertenciaDTO.numero( advertencia.getNumero() );
        advertenciaDTO.titulo( advertencia.getTitulo() );
        advertenciaDTO.descricao( advertencia.getDescricao() );

        return advertenciaDTO.build();
    }

    protected List<AdvertenciaDTO> advertenciaSetToAdvertenciaDTOList(Set<Advertencia> set) {
        if ( set == null ) {
            return null;
        }

        List<AdvertenciaDTO> list = new ArrayList<AdvertenciaDTO>( set.size() );
        for ( Advertencia advertencia : set ) {
            list.add( advertenciaToAdvertenciaDTO( advertencia ) );
        }

        return list;
    }

    protected InformacoesMatriculaDTO informacoesMatriculaToInformacoesMatriculaDTO(InformacoesMatricula informacoesMatricula) {
        if ( informacoesMatricula == null ) {
            return null;
        }

        InformacoesMatriculaDTO.InformacoesMatriculaDTOBuilder informacoesMatriculaDTO = InformacoesMatriculaDTO.builder();

        informacoesMatriculaDTO.id( informacoesMatricula.getId() );
        informacoesMatriculaDTO.esteveOutraCreche( informacoesMatricula.getEsteveOutraCreche() );
        informacoesMatriculaDTO.razaoSaidaCreche( informacoesMatricula.getRazaoSaidaCreche() );
        informacoesMatriculaDTO.tipoResidencia( informacoesMatricula.getTipoResidencia() );
        informacoesMatriculaDTO.valorAluguel( informacoesMatricula.getValorAluguel() );
        informacoesMatriculaDTO.beneficiarioGoverno( informacoesMatricula.getBeneficiarioGoverno() );
        informacoesMatriculaDTO.valorBeneficio( informacoesMatricula.getValorBeneficio() );
        informacoesMatriculaDTO.rendaFamiliar( informacoesMatricula.getRendaFamiliar() );
        informacoesMatriculaDTO.paisCasados( informacoesMatricula.getPaisCasados() );
        informacoesMatriculaDTO.moramJuntos( informacoesMatricula.getMoramJuntos() );
        informacoesMatriculaDTO.observacao( informacoesMatricula.getObservacao() );

        return informacoesMatriculaDTO.build();
    }

    protected List<DocumentoMatriculaDTO> documentoMatriculaSetToDocumentoMatriculaDTOList(Set<DocumentoMatricula> set) {
        if ( set == null ) {
            return null;
        }

        List<DocumentoMatriculaDTO> list = new ArrayList<DocumentoMatriculaDTO>( set.size() );
        for ( DocumentoMatricula documentoMatricula : set ) {
            list.add( documentoMatriculaMapperImpl.toDTO( documentoMatricula ) );
        }

        return list;
    }

    protected Endereco matriculaDTOToEndereco(MatriculaDTO matriculaDTO) {
        if ( matriculaDTO == null ) {
            return null;
        }

        Endereco.EnderecoBuilder endereco = Endereco.builder();

        endereco.id( matriculaDTO.getEnderecoId() );

        return endereco.build();
    }

    protected Pessoa matriculaDTOToPessoa(MatriculaDTO matriculaDTO) {
        if ( matriculaDTO == null ) {
            return null;
        }

        Pessoa.PessoaBuilder pessoa = Pessoa.builder();

        pessoa.nome( matriculaDTO.getNome() );
        pessoa.cpf( matriculaDTO.getCpf() );

        return pessoa.build();
    }

    protected Turma turmaDTOToTurma(TurmaDTO turmaDTO) {
        if ( turmaDTO == null ) {
            return null;
        }

        Turma.TurmaBuilder turma = Turma.builder();

        turma.id( turmaDTO.getId() );
        turma.titulo( turmaDTO.getTitulo() );
        turma.nomeProfessor( turmaDTO.getNomeProfessor() );
        turma.ano( turmaDTO.getAno() );
        turma.horaInicio( turmaDTO.getHoraInicio() );
        turma.horaFim( turmaDTO.getHoraFim() );
        turma.turno( turmaDTO.getTurno() );
        turma.telefoneProfessor( turmaDTO.getTelefoneProfessor() );
        turma.quantidadeAlunos( turmaDTO.getQuantidadeAlunos() );

        return turma.build();
    }

    protected List<Tutor> tutorDTOListToTutorList(List<TutorDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Tutor> list1 = new ArrayList<Tutor>( list.size() );
        for ( TutorDTO tutorDTO : list ) {
            list1.add( tutorMapperImpl.toModelo( tutorDTO ) );
        }

        return list1;
    }

    protected Set<NecessidadeEspecial> necessidadeEspecialDTOListToNecessidadeEspecialSet(List<NecessidadeEspecialDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<NecessidadeEspecial> set = new LinkedHashSet<NecessidadeEspecial>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( NecessidadeEspecialDTO necessidadeEspecialDTO : list ) {
            set.add( necessidadeEspecialMapperImpl.toModelo( necessidadeEspecialDTO ) );
        }

        return set;
    }

    protected Set<Responsavel> responsavelDTOListToResponsavelSet(List<ResponsavelDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<Responsavel> set = new LinkedHashSet<Responsavel>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( ResponsavelDTO responsavelDTO : list ) {
            set.add( responsavelMapperImpl.toModelo( responsavelDTO ) );
        }

        return set;
    }

    protected Advertencia advertenciaDTOToAdvertencia(AdvertenciaDTO advertenciaDTO) {
        if ( advertenciaDTO == null ) {
            return null;
        }

        Advertencia.AdvertenciaBuilder advertencia = Advertencia.builder();

        advertencia.numero( advertenciaDTO.getNumero() );
        advertencia.titulo( advertenciaDTO.getTitulo() );
        advertencia.descricao( advertenciaDTO.getDescricao() );

        return advertencia.build();
    }

    protected Set<Advertencia> advertenciaDTOListToAdvertenciaSet(List<AdvertenciaDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<Advertencia> set = new LinkedHashSet<Advertencia>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( AdvertenciaDTO advertenciaDTO : list ) {
            set.add( advertenciaDTOToAdvertencia( advertenciaDTO ) );
        }

        return set;
    }

    protected InformacoesMatricula informacoesMatriculaDTOToInformacoesMatricula(InformacoesMatriculaDTO informacoesMatriculaDTO) {
        if ( informacoesMatriculaDTO == null ) {
            return null;
        }

        InformacoesMatricula.InformacoesMatriculaBuilder informacoesMatricula = InformacoesMatricula.builder();

        informacoesMatricula.id( informacoesMatriculaDTO.getId() );
        informacoesMatricula.esteveOutraCreche( informacoesMatriculaDTO.getEsteveOutraCreche() );
        informacoesMatricula.razaoSaidaCreche( informacoesMatriculaDTO.getRazaoSaidaCreche() );
        informacoesMatricula.tipoResidencia( informacoesMatriculaDTO.getTipoResidencia() );
        informacoesMatricula.valorAluguel( informacoesMatriculaDTO.getValorAluguel() );
        informacoesMatricula.beneficiarioGoverno( informacoesMatriculaDTO.getBeneficiarioGoverno() );
        informacoesMatricula.valorBeneficio( informacoesMatriculaDTO.getValorBeneficio() );
        informacoesMatricula.rendaFamiliar( informacoesMatriculaDTO.getRendaFamiliar() );
        informacoesMatricula.paisCasados( informacoesMatriculaDTO.getPaisCasados() );
        informacoesMatricula.moramJuntos( informacoesMatriculaDTO.getMoramJuntos() );
        informacoesMatricula.observacao( informacoesMatriculaDTO.getObservacao() );

        return informacoesMatricula.build();
    }

    protected Set<DocumentoMatricula> documentoMatriculaDTOListToDocumentoMatriculaSet(List<DocumentoMatriculaDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<DocumentoMatricula> set = new LinkedHashSet<DocumentoMatricula>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( DocumentoMatriculaDTO documentoMatriculaDTO : list ) {
            set.add( documentoMatriculaMapperImpl.toModelo( documentoMatriculaDTO ) );
        }

        return set;
    }
}
