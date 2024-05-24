package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.MatriculaListagemDTO;
import br.ueg.piasi.MatricuLAR.dto.TurmaDTO;
import br.ueg.piasi.MatricuLAR.model.Matricula;
import br.ueg.piasi.MatricuLAR.model.Turma;
import br.ueg.prog.webi.api.model.IEntidade;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-22T23:29:32-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Amazon.com Inc.)"
)
@Component
public class TurmaMapperImpl implements TurmaMapper {

    @Override
    public Turma toModelo(TurmaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Turma.TurmaBuilder turma = Turma.builder();

        turma.id( dto.getId() );
        turma.titulo( dto.getTitulo() );
        turma.nomeProfessor( dto.getNomeProfessor() );
        turma.ano( dto.getAno() );
        turma.horaInicio( dto.getHoraInicio() );
        turma.horaFim( dto.getHoraFim() );
        turma.turno( dto.getTurno() );
        turma.telefoneProfessor( dto.getTelefoneProfessor() );
        turma.alunos( matriculaListagemDTOListToMatriculaSet( dto.getAlunos() ) );
        turma.quantidadeAlunos( dto.getQuantidadeAlunos() );

        return turma.build();
    }

    @Override
    public List<TurmaDTO> toDTO(List<Turma> lista) {
        if ( lista == null ) {
            return null;
        }

        List<TurmaDTO> list = new ArrayList<TurmaDTO>( lista.size() );
        for ( Turma turma : lista ) {
            list.add( toDTO( turma ) );
        }

        return list;
    }

    @Override
    public void updateModel(Turma source, Turma target) {
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
        if ( source.getTitulo() != null ) {
            target.setTitulo( source.getTitulo() );
        }
        if ( source.getNomeProfessor() != null ) {
            target.setNomeProfessor( source.getNomeProfessor() );
        }
        if ( source.getAno() != null ) {
            target.setAno( source.getAno() );
        }
        if ( source.getHoraInicio() != null ) {
            target.setHoraInicio( source.getHoraInicio() );
        }
        if ( source.getHoraFim() != null ) {
            target.setHoraFim( source.getHoraFim() );
        }
        if ( source.getTurno() != null ) {
            target.setTurno( source.getTurno() );
        }
        if ( source.getTelefoneProfessor() != null ) {
            target.setTelefoneProfessor( source.getTelefoneProfessor() );
        }
        if ( target.getAlunos() != null ) {
            Set<Matricula> set = source.getAlunos();
            if ( set != null ) {
                target.getAlunos().clear();
                target.getAlunos().addAll( set );
            }
        }
        else {
            Set<Matricula> set = source.getAlunos();
            if ( set != null ) {
                target.setAlunos( new LinkedHashSet<Matricula>( set ) );
            }
        }
        if ( source.getQuantidadeAlunos() != null ) {
            target.setQuantidadeAlunos( source.getQuantidadeAlunos() );
        }
    }

    @Override
    public TurmaDTO toDTO(Turma turma) {
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

        turmaDTO.setAlunos( toMatriculaListagemDTO(turma.getAlunos()) );
        turmaDTO.setQuantidadeAlunos( getQuantidadeAlunos(turma.getAlunos()) );

        return turmaDTO;
    }

    protected Matricula matriculaListagemDTOToMatricula(MatriculaListagemDTO matriculaListagemDTO) {
        if ( matriculaListagemDTO == null ) {
            return null;
        }

        Matricula.MatriculaBuilder matricula = Matricula.builder();

        return matricula.build();
    }

    protected Set<Matricula> matriculaListagemDTOListToMatriculaSet(List<MatriculaListagemDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<Matricula> set = new LinkedHashSet<Matricula>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( MatriculaListagemDTO matriculaListagemDTO : list ) {
            set.add( matriculaListagemDTOToMatricula( matriculaListagemDTO ) );
        }

        return set;
    }
}
