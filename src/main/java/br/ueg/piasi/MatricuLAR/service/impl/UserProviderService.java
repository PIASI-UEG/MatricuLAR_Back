package br.ueg.piasi.MatricuLAR.service.impl;


import br.ueg.piasi.MatricuLAR.controller.UsuarioController;
import br.ueg.piasi.MatricuLAR.dto.UsuarioDTO;
import br.ueg.prog.webi.api.dto.CredencialDTO;
import br.ueg.prog.webi.api.dto.UsuarioSenhaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

//TODO resolver a parte do usuario aqui dentro
@Service
public class UserProviderService implements br.ueg.prog.webi.api.service.UserProviderService {

    @Autowired
    UsuarioController usuarioController;


    @Autowired
    UsuarioServiceImpl usuarioService;


    @Override
    public CredencialDTO getCredentialByLogin(String usuarioLogin) {

        return getCredencialDTO(usuarioService.getUsuarioDTOPorLogin(usuarioLogin));
    }


    private CredencialDTO getCredencialDTO(UsuarioDTO user) {

        System.out.println(user.toString());

        return CredencialDTO.builder()
                .login(user.getLogin())
                .id(user.getCodigo())
                .nome(user.getPessoaNome())
                .roles(Collections.singletonList(user.getCargo()))
                .statusAtivo(true)
                .senha(user.getSenha())
                .build();
    }


    @Override
    public CredencialDTO redefinirSenha(UsuarioSenhaDTO usuarioSenhaDTO) {
        return null;
    }

    @Override
    public CredencialDTO getCredentialByEmail(String email) {
        return null;
    }
}