package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.dto.UsuarioDTO;
import br.ueg.piasi.MatricuLAR.mapper.UsuarioMapper;
import br.ueg.piasi.MatricuLAR.model.Usuario;
import br.ueg.piasi.MatricuLAR.repository.UsuarioRepository;
import br.ueg.piasi.MatricuLAR.service.UsuarioService;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl extends BaseCrudService<Usuario, Long, UsuarioRepository>
        implements UsuarioService {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PessoaServiceImpl pessoaService;

    @Override
    protected void prepararParaIncluir(Usuario usuario) {

    }

    @Override
    protected void validarDados(Usuario usuario) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String senhaCodificada = bCryptPasswordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCodificada);
    }

    @Override
    protected void validarCamposObrigatorios(Usuario entidade) {

    }

    public UsuarioDTO getUsuarioDTOPorPessoaCpf(String usuarioPessoaCpf) {

        Usuario usuario = repository.findUsuarioByPessoaCpf(usuarioPessoaCpf).orElseThrow();

        UsuarioDTO usuarioDTO = usuarioMapper.toDTO(usuario);
        usuarioDTO.setSenha(usuario.getSenha());

        return usuarioDTO;
    }

    public List<Usuario> findUsuarioWithSortAsc(String field){
        return this.repository.findAll(Sort.by(Sort.Direction.ASC,field));
    }

    public List<Usuario> findUsuarioWithPagination(int offset, int pageSize){
        return  this.repository.findUsuariosWithPagination(offset, pageSize);
    }

    public Integer countRows(){
        return this.repository.countAll();
    }

}
