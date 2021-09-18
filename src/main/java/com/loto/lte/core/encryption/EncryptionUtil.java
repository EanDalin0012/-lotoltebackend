package com.loto.lte.core.encryption;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.loto.lte.core.constant.DemoProperties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public class EncryptionUtil {
    private static final String AES = "AES"; // Default uses ECB PKCS5Padding

    public static String encrypt(String Data, String secret) throws Exception {
        Key key = generateKey(secret);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.getEncoder().encodeToString(encVal);
        return encryptedValue;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            Key key = generateKey(secret);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    private static Key generateKey(String secret) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(secret.getBytes());
        Key key = new SecretKeySpec(decoded, AES);
        return key;
    }

    public static String decodeKey(String str) {
        byte[] decoded = Base64.getDecoder().decode(str.getBytes());
        return new String(decoded);
    }

    public static String encodeKey(String str) {
        byte[] encoded = Base64.getEncoder().encode(str.getBytes());
        return new String(encoded);
    }

    public static void main(String a[]) throws Exception {
        String secretKey = DemoProperties.secretKey;
        String encodedBase64Key = encodeKey(secretKey);
        System.out.println("EncodedBase64Key = " + encodedBase64Key); // This need to be share between client and server
        // To check actual key from encoded base 64 secretKey
        // String toDecodeBase64Key = decodeKey(encodedBase64Key);
        // System.out.println("toDecodeBase64Key = "+toDecodeBase64Key);
        Map<String, Object> data = new LinkedHashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        data.put("id", 1);
        data.put("name", "name");
        data.put("gender", "male");
        String rawdata = objectMapper.writeValueAsString(data);


        // AES Encryption based on above secretKey
        String encrStr = EncryptionUtil.encrypt(rawdata, encodedBase64Key);
        System.out.println("Cipher Text: Encryption of str = " + encrStr);
        // AES Decryption based on above secretKey
        String d = "obTEUoNa+7ZkNMbhWUtbVQ==";
        String decrStr = EncryptionUtil.decrypt(d, encodedBase64Key);
        System.out.println("Decryption of str = " + decrStr);
    }
}
