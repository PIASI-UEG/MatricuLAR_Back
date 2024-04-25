package br.ueg.piasi.MatricuLAR.util;

import br.ueg.piasi.MatricuLAR.dto.AssinaturaDTO;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;


public class RemetenteAssiDig {

    private PublicKey pubKey;

    public PublicKey getPubKey() {
        return pubKey;
    }

    public void setPubKey(PublicKey pubKey) {
        this.pubKey = pubKey;
    }


    public byte[] geraAssinatura(File termo, AssinaturaDTO assinaturaDTO) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance("DSA");
        String seed = assinaturaDTO.getImagemAss().concat(assinaturaDTO.getCpfResponsavel());

        //Geração das chaves públicas e privadas
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        SecureRandom secRan = new SecureRandom(seed.getBytes());
        kpg.initialize(512, secRan);
        KeyPair keyP = kpg.generateKeyPair();
        this.pubKey = keyP.getPublic();
        PrivateKey priKey = keyP.getPrivate();

        //Inicializando Obj Signature com a Chave Privada
        sig.initSign(priKey);

        sig.update(termo.toString().getBytes());
        //assina
        byte[] assinatura = sig.sign();

        return assinatura;
    }

}
