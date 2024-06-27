package br.ueg.piasi.MatricuLAR.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Email {


    private static final String REMETENTE = "contato@associacaosagradafamilia.com.br";

//    @Value("${email-senha}")
    private static String SENHA = "SenhaDoEmail";


    private static MultiPartEmail montaEmailReseteSenha(String destinatario, String novaSenha) {
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("email-ssl.com.br");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(REMETENTE, SENHA));
        email.setSSLOnConnect(true);

        try {
            email.setFrom(REMETENTE);
            email.setSubject("Nova senha sistema MatricuLAR");
            email.setMsg("Sua nova senha é: " + novaSenha + ", já pode fazer o login e altera-lá no menu: Minha Conta");
            email.addTo(destinatario);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
        return email;
    }

    public static void enviaEmail(String destinatario, String novaSenha){
        MultiPartEmail email = montaEmailReseteSenha(destinatario, novaSenha);
        try {
            email.send();
        } catch (EmailException emailException) {
            emailException.printStackTrace();
        }
    }


}
