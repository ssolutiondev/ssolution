package com.ssolution.admin.system.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AESCipher {


    public static String encrypt (String plaintext,byte[] secretKey) throws Exception
    {
        //Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        //Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(secretKey, "AES");

        // Generating IV.
        byte[] IV = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);

        //Create IvParameterSpec
        IvParameterSpec ivSpec = new IvParameterSpec(IV);

        System.out.println(new String(IV));

        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plaintext.getBytes("UTF-8"));
        byte[] encryptText = new byte[IV.length + cipherText.length];
        System.arraycopy(IV, 0, encryptText, 0, IV.length);
        System.arraycopy(cipherText, 0, encryptText, IV.length, cipherText.length);
        return Base64.getEncoder().encodeToString(encryptText);
    }

    public static String decrypt (String encrypedText, byte[] secretKey) throws Exception
    {

        byte[] cipherText = Base64.getDecoder().decode(encrypedText);
        //Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] iv = Arrays.copyOfRange(cipherText, 0, 16);
        byte[] text = Arrays.copyOfRange(cipherText, 16, cipherText.length);

        //Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(secretKey, "AES");
        //Create IvParameterSpec
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        //Perform Decryption
        byte[] decryptedText = cipher.doFinal(text);
        return new String(decryptedText);
    }
}
