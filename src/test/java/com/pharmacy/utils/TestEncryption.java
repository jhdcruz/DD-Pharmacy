package com.pharmacy.utils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TestEncryption {

    /**
     * Test encryption and decryption of a string
     */
    @Test
    public void testEncryption() {
        EncryptionUtils encryptionUtils = new EncryptionUtils();
        byte[] secretKey = encryptionUtils.generateKeyBytes();

        String message = "admin";

        byte[] cipherText = encryptionUtils.encrypt(message, secretKey);
        String decrypted = encryptionUtils.decrypt(cipherText, secretKey);


        System.out.println("Original: " + message);
        System.out.println("Secret Key: " + Arrays.toString(secretKey));
        System.out.println("Encrypted: " + Arrays.toString(cipherText));
        System.out.println("Decrypted: " + decrypted);

        assertEquals(message, decrypted);
    }
}
