package br.ueg.piasi.MatricuLAR.mapper;

import br.ueg.piasi.MatricuLAR.dto.TurmaDTO;
import br.ueg.piasi.MatricuLAR.model.Turma;
import br.ueg.prog.webi.api.model.IEntidade;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-17T15:38:01-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
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
        turma.quantidadeAlunos( dto.getQuantidadeAlunos() );

        return turma.build();
    }

    @Override
    public TurmaDTO toDTO(Turma modelo) {
        if ( modelo == null ) {
            return null;
        }

        TurmaDTO turmaDTO = new TurmaDTO();

        turmaDTO.setId( modelo.getId() );
        turmaDTO.setTitulo( modelo.getTitulo() );
        turmaDTO.setNomeProfessor( modelo.getNomeProfessor() );
        turmaDTO.setTurno( modelo.getTurno() );
        turmaDTO.setAno( modelo.getAno() );
        turmaDTO.setHoraInicio( modelo.getHoraInicio() );
        turmaDTO.setHoraFim( modelo.getHoraFim() );
        turmaDTO.setTelefoneProfessor( modelo.getTelefoneProfessor() );
        turmaDTO.setQuantidadeAlunos( modelo.getQuantidadeAlunos() );

        return turmaDTO;
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
        if ( source.getQuantidadeAlunos() != null ) {
            target.setQuantidadeAlunos( source.getQuantidadeAlunos() );
        }
    }
}
