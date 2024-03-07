package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.dto.UsuarioDTO;
import br.ueg.piasi.MatricuLAR.enums.Cargo;
import br.ueg.piasi.MatricuLAR.mapper.UsuarioMapper;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Usuario;
import br.ueg.piasi.MatricuLAR.repository.UsuarioRepository;
import br.ueg.piasi.MatricuLAR.service.UsuarioService;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static br.ueg.piasi.MatricuLAR.exception.SistemaMessageCode.*;

@Service
public class UsuarioServiceImpl extends BaseCrudService<Usuario, Long, UsuarioRepository>
        implements UsuarioService {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PessoaServiceImpl pessoaService;

    @Override
    protected void prepararParaIncluir(Usuario usuario) {
       usuario.setPessoa(pessoaService.incluir(
               Pessoa.builder()
                        .nome(usuario.getPessoa().getNome())
                        .cpf(usuario.getPessoa().getCpf())
                        .telefone(usuario.getPessoa().getTelefone())
                .build()
               )
       );
        criptografarSenha(usuario);
    }

    @Override
    protected void validarDados(Usuario usuario) {

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


    private void criptografarSenha(Usuario usuario) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String senhaCodificada = bCryptPasswordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCodificada);
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



    public Usuario alterar(Usuario entidade, Long id, Long idUsuarioRequisicao) {

        if (id.equals(idUsuarioRequisicao) || editorEhAdmin(idUsuarioRequisicao)) {
            if (entidade.getSenha() == null || entidade.getSenha().isEmpty()) {
                Usuario usuario = repository.findById(id).orElse(null);
                if (Objects.isNull(usuario)) {
                    throw new BusinessException(ERRO_USUARIO_NAO_EXISTE);
                }
                entidade.setSenha(usuario.getSenha());
            } else {
                criptografarSenha(entidade);
            }

            return super.alterar(entidade, id);
        }

        throw new BusinessException(ERRO_SOMENTE_DONO_ALTERA_SENHA);
    }

    private boolean editorEhAdmin(Long idUsuarioRequisicao) {
        return Cargo.ADMIN.equals(repository.findById(idUsuarioRequisicao).get().getCargo());
    }

    @Override
    public Usuario excluir(Long id) {

        if (ehAdmin(id)){
            throw new BusinessException(ERRO_EXCLUIR_ADMIN);
        }

        return super.excluir(id);
    }

    private boolean ehAdmin(Long id) {
        Usuario usuario = repository.findById(id).orElse(null);
        return (Objects.nonNull(usuario) && usuario.getCargo().equals(Cargo.ADMIN));
    }
}
