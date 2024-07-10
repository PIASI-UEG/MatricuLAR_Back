package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.dto.RedefinirSenhaDTO;
import br.ueg.piasi.MatricuLAR.dto.UsuarioDTO;
import br.ueg.piasi.MatricuLAR.enums.Cargo;
import br.ueg.piasi.MatricuLAR.exception.SistemaMessageCode;
import br.ueg.piasi.MatricuLAR.mapper.UsuarioMapperImpl;
import br.ueg.piasi.MatricuLAR.model.Pessoa;
import br.ueg.piasi.MatricuLAR.model.Usuario;
import br.ueg.piasi.MatricuLAR.repository.UsuarioRepository;
import br.ueg.piasi.MatricuLAR.service.UsuarioService;
import br.ueg.piasi.MatricuLAR.util.Email;
import br.ueg.prog.webi.api.exception.BusinessException;
import br.ueg.prog.webi.api.service.BaseCrudService;
import org.apache.commons.lang3.RandomStringUtils;
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
    private UsuarioMapperImpl usuarioMapper;
    @Autowired
    private Email emailService;
    @Autowired
    private PessoaServiceImpl pessoaService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void prepararParaIncluir(Usuario usuario) {
        Pessoa pessoa = pessoaService.findByCPF(usuario.getPessoa().getCpf());
        if(pessoa != null) {
            usuario.setPessoa(pessoa);
        }else{
            usuario.setPessoa(pessoaService.incluir(
                            Pessoa.builder()
                                    .nome(usuario.getPessoa().getNome())
                                    .cpf(usuario.getPessoa().getCpf())
                                    .telefone(usuario.getPessoa().getTelefone())
                                    .build()
                    )
            );
        }
        criptografarSenha(usuario);
    }

    @Override
    protected void validarDados(Usuario usuario) {
        if(usuario.getCargo().equals(Cargo.ADMIN)){
            Usuario usuarioAdmin = usuarioRepository.findByCargo(Cargo.ADMIN);
            if (Objects.nonNull(usuarioAdmin) && !Objects.equals(usuarioAdmin.getId(), usuario.getId()))
                throw new BusinessException(ERRO_JA_EXISTE_USUARIO_ADMIN);
        }
    }

    @Override
    protected void validarCamposObrigatorios(Usuario entidade) {

    }

    public UsuarioDTO getUsuarioDTOPorPessoaCpf(String usuarioPessoaCpf) {

        Usuario usuario = repository.findUsuarioByPessoaCpf(usuarioPessoaCpf).orElse(null);

        if(Objects.nonNull(usuario)){
            UsuarioDTO usuarioDTO = usuarioMapper.toDTO(usuario);
            usuarioDTO.setSenha(usuario.getSenha());
            return usuarioDTO;
        }

        throw new BusinessException(ERRO_USUARIO_NAO_EXISTE);
    }


    private void criptografarSenha(Usuario usuario) {
        String senha = usuario.getSenha();
        if (senha != null && !senha.isBlank()){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String senhaCodificada = bCryptPasswordEncoder.encode(senha);
            usuario.setSenha(senhaCodificada);
        }else{
            throw new BusinessException(SistemaMessageCode.ERRO_SENHA_NAO_PODE_SER_VAZIA);
        }

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


    private boolean validaSenhaAntiga(String senhaAntiga, String senhaAntigaAValidar){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(senhaAntigaAValidar, senhaAntiga);
    }

    public Usuario alterar(Usuario entidade, Long id, Long idUsuarioRequisicao, String senhaAntigaAValidar) {

        if (id.equals(idUsuarioRequisicao) || editorEhAdmin(idUsuarioRequisicao)) {
            Usuario usuario = repository.findById(id).orElse(null);
            if (Objects.isNull(usuario)) {
                throw new BusinessException(ERRO_USUARIO_NAO_EXISTE);
            }
            if (entidade.getSenha() == null || entidade.getSenha().isEmpty()) {
                entidade.setSenha(usuario.getSenha());
            } else {
                if (!validaSenhaAntiga(usuario.getSenha(), senhaAntigaAValidar)){
                    throw new BusinessException(SENHA_ANTIGA_INCORRETA);
                }
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

    public void redefinirSenha(RedefinirSenhaDTO dadosRefinirSenha) {

        Usuario usuario = repository.findUsuarioByPessoaCpf(dadosRefinirSenha.cpf()).orElse(null);

        if (Objects.nonNull(usuario)){

            if (usuario.getEmail().equals(dadosRefinirSenha.email())){
                String novaSenha = RandomStringUtils.randomAlphanumeric(7,11);
                usuario.setSenha(novaSenha);
                criptografarSenha(usuario);
                alterar(usuario, usuario.getId());
                Thread threadEnviaEmail = new Thread( () ->{
                    emailService.enviaEmail(usuario.getEmail(), novaSenha);
                });
               threadEnviaEmail.start();
               return;
            }
            throw new BusinessException(ERRO_EMAIL_INCORRETO);
        }

        throw new BusinessException(ERRO_USUARIO_NAO_EXISTE);
    }
}
