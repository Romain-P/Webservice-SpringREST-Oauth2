package com.ortec.gta.auth;

import javax.annotation.Nullable;
import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Author: romain.pillot
 * @Date: 06/10/2017
 */
public class RsaHandler {
    private final String privateKey;
    private final String publicKey;

    /**
     * Create a new RsaHandler with a base64-encoded key-pair.
     *
     * @param publicKey a nullable rsa public key. Don't call decrypt methods if there is no public key provided.
     * @param privateKey a nullable rsa private key. Don't call encrypt methods if there is not private key provided.
     */
    public RsaHandler(@Nullable String publicKey, @Nullable String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * @param plaintext human readable text to encrypt.
     * @return the text encrypted from the private key (key format must be PKCS8)
     * @throws RuntimeException in case of no private key provided.
     */
    public String encrypt(String plaintext) throws Exception {
        if (this.privateKey == null)
            throw new RuntimeException("Can't encrypt the message, you didn't specify the public key.");

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, parsePublicKey(publicKey));

        byte[] bytes = cipher.doFinal(plaintext.getBytes(Charset.forName("UTF-8")));

        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * @param cipherText encrypted text to make human readable.
     * @return the text decrypted from the public key (key format must be X509)
     * @throws RuntimeException in case of no public key provided.
     */
    public String decrypt(String cipherText) throws Exception {
        if (this.publicKey == null)
            throw new RuntimeException("Can't decrypt the message, you didn't specify the private key.");

        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, parsePrivateKey(privateKey));

        return new String(cipher.doFinal(bytes), Charset.forName("UTF-8"));
    }

    private PublicKey parsePublicKey(String base64Key) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64Key.getBytes());
        KeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePublic(spec);
    }

    private PrivateKey parsePrivateKey(String base64key) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64key.getBytes());
        KeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePrivate(spec);
    }
}
