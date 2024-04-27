package br.ueg.piasi.MatricuLAR.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;


public class DestinatarioAssiDig {

    public void validaAssinatura(PublicKey pubKey, MultipartFile termo, byte[] hash) throws
            NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        Signature clientSig = Signature.getInstance("RSA");
        clientSig.initVerify(pubKey);
        clientSig.update(termo.getBytes());

        //verifica assinatura
        if (clientSig.verify(hash)) {
            //Mensagem corretamente assinada
            System.out.println("A Mensagem recebida foi assinada corretamente.");
        } else {
            //Mensagem não pode ser validada
            System.out.println("A Mensagem recebida NÃO pode ser validada.");
        }
    }

}
