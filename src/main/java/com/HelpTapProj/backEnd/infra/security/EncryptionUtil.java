package com.HelpTapProj.backEnd.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private static final String ALGORITHM = "AES";

    @Value("${encryption.secret}")
    private String encryptionSecret;

    private SecretKeySpec getKey(){
        byte[] key = encryptionSecret.getBytes();
        byte[] keyBytes = new byte[16];
        System.arraycopy(key,0,keyBytes,0,Math.min(key.length, keyBytes.length));
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    public String encrypt(String data) throws Exception{
        if (data == null || data.isEmpty()){
            return data;
        }
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getKey());
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return java.util.Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e){
            throw new Exception("Error while encrypting data", e);
        }
    }

    public String decrypt(String encryptedData) throws Exception{
        if (encryptedData == null || encryptedData.isEmpty()){
            return encryptedData;
        }
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getKey());
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        } catch (Exception e){
            throw new Exception("Error while decrypting data", e);
        }
    }

}
