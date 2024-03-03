package br.ueg.piasi.MatricuLAR.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Email {

    private static final String REMETENTE = "ademirodostestes@gmail.com"; //email de testes
    private static final String SENHA = "ciyp vehk qgwe gyhd"; //senha de app do google


    private static MultiPartEmail setEmail(String destinatario) {
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(REMETENTE, SENHA));
        email.setSSLOnConnect(true);

        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        try {
            email.setFrom(REMETENTE);
            email.setSubject("Mensagem de teste");
            email.setMsg("Esse é um teste para testar " + String.format("%06d", number));
            email.addTo(destinatario);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
        return email;
    }

    public static void enviaEmail(String destinatario){
        MultiPartEmail email = setEmail(destinatario);
        try {
            email.send();
        } catch (EmailException emailException) {
            emailException.printStackTrace();
        }
    }


}
